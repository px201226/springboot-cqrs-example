
## 개요
- 카카오 API 연동 및 키워드 블로그 검색 (정확도순, 최신순) 페이징 조회
- 인기 검색어 목록 조회
- 멀티 모듈 구성 및 이벤트 기반 CQRS 패턴 적용


## 기술 스택
- 언어: Java 11
- 프레임워크 : Spring Boot 2
- 데이터베이스 : H2 (MySQL)
- 빌드 도구 : Gradle

  
## 프로젝트 구조
멀티 모듈 기반, 이벤트 기반의 CQRS 패턴을 적용하여 읽기 모델과 쓰기 모델을 분리합니다.

- `blog-api` 모듈
  - 키워드 검색, 인기 검색어 조회 API 앤드포인트 및 인터페이스를 정의합니다.
  - `blog-reader` 모듈의 읽기 모델을 이용해 API 응답값을 반환합니다.
- `blog-reader` 모듈
    - 키워드 검색, 인기 검색어 조회에 대한 읽기 모델을 정의하고 구현합니다.
    - 키워드 검색 시, 키워드가 검색되었다는 이벤트를 발행합니다.
- `blog-core` 모듈
    - 키워드가 검색되었다는 이벤트를 수신합니다.
    - 이벤트를 수신하면, 조회수를 증가시키는 등의 도메인 로직을 수행합니다.
- `blog-common` 모듈
  - 공통 모듈로 각종 DTO 객체를 정의합니다.

  
## 모듈별 컴포넌트 및 상호작용 다이어그램
각 모듈간의 호출은 같은 JVM 위에서 이루어지지만, 약간의 코드 작업으로 메시지 큐와 원격 호출로 대체할 수 있도록 느슨한 결합을 가지도록 합니다.

![module-component-diagram.png](./img/module-component-diagram.png)

클라이언트가 서비스 API 호출 시, 발생되는 플로우
1. `blog-api` 클라이언트 요청을 처리하기 위해 적절한 읽기 모델을 호출합니다.
2. `blog-reader` 호출된 읽기 모델은 외부 API 또는 Repository 를 통해 응답 데이터를 반환합니다.
3. `blog-reader` 읽기 모델에 호출이 발생하면 해당하는 도메인 이벤트를 발행합니다.
4. `blog-core` 도메인 이벤트를 수신하고, 이벤트 디스패처에 처리를 위임합니다.

   
## 주요 기능 구현
**1. 블로그 검색**
- 카카오 API 연동은 OpenFeign 라이브러리를 사용합니다.
- OpenFeign 은 선언전 REST 클라이언트로, REST API 연동에 필요한 반복적인 코드를 줄일 수 있습니다.

  
**2. 인기 검색어 목록**
- 인기 김색어 목록을 구현하기 위해, 키워드별 검색횟수를 저장할 수 있는 테이블을 정의합니다.
```sql
create table KEYWORD_ANALYTICS
(
    id           bigint       NOT NULL AUTO_INCREMENT,
    keyword      varchar(255) not null,
    search_count bigint       not null,
    create_at    datetime(3) not null,
    primary key (id)
);
```
```SQL
create unique index UIX01_KEYWORD_ANALYTICS
    on KEYWORD_ANALYTICS (keyword);

create index IX01_KEYWORD_ANALYTICS
    on KEYWORD_ANALYTICS (search_count, id);
```

```SQL
EXPLAIN ANALYZE
SELECT * FROM `KEYWORD_ANALYTICS`
ORDER BY search_count DESC
LIMIT 10;
```
``` SQL
-> Limit: 10 row(s)  (cost=0.01 rows=10) (actual time=1.025..1.090 rows=10 loops=1)
  -> Index scan on KEYWORD_ANALYTICS using IX01_KEYWORD_ANALYTICS (reverse)  (cost=0.01 rows=10) (actual time=1.014..1.062 rows=10 loops=1)
```
- 인기 검색어 Top 10 쿼리 및 실행 계획
  - Index Range Scan 으로 10건의 데이터에 대해서 I/O가 일어난 것을 확인할 수 있습니다.


   
**3. 멀티 모듈 설계**
- 해당 예제의 핵심 도메인은 **키워드 분석 도메인** 입니다.
- 키워드 분석 도메인은 검색 API의 구현과 관계없이 키워드를 분석할 수 있어야 합니다.
- 키워드 분석 모델의 상태를 변경시 킬 수 있는 것은 키워드가 검색되었다는 이벤트 뿐 입니다.
키워드 분석 도메인은 core 모듈로 설계하였고, 이벤트를 수신/발신하는 채널은 Spring ApplicationEvent를 사용합니다.
- 그래서 최종적으로 이벤트 드리븐 기반 CQRS 패턴의 모듈 설계가 되었습니다.
  - `api`,`reader`,`core`,`common` 모듈

  
**4. 트래픽 및 동시성 이슈 처리**

**트래픽 관리**
- 읽기 모델과 쓰기 모델이 이벤트로 통신하기 때문에, 서버 분리가 가능합니다.
- 읽기 트래픽 많으면 읽기 모델 서버만 스케일 아웃할 수 있습니다.
- 쓰기 모델은 이벤트 기반 비동기 처리가 가능해, 쓰기 지연이 발생하더라도 읽기 모델에 영향을 주지 않습니다.  

  
**동시성 이슈 관리**
- JPA를 사용하여 엔티티를 업데이트 하기 위해서는 일반적으로 `SELECT`, `UPDATE` 두 번의 SQL이 호출되기 때문에 갱신손실 문제와 같은 동시성 이슈가 발생할 수 있습니다.
- 가장 쉬운 해결 방법은 검색 횟수 업데이트 트랜잭션을 원자적 연산으로 처리하는 것 입니다.

`UPDATE keyword_analytics SET search_count = search_count + 1 WHERE keyword = 'xx';`
- 하지만, 이러한 방식은 객체 중심적이지 않고 새로운 키워드를 저장하는 경우에는 저장된 데이터가 있는지 확인하는 작업이 들어가`SELECT`, `INSERT` 두 번의 호출이 필요합니다.
- 이 문제를 해결하기 위해서는 검색 횟수 업데이트 트랜잭션에서 `SELECT FOR UPDATE` 문을 사용하여 읽기 및 쓰기 잠금을 사용하여 동시성 문제를 회피할 수 있습니다.  


  
## 테스트 코드
각 모듈의 관심사별로 테스트 코드를 작성하였습니다.

- **api 모듈 테스트 케이스**
![api-test.png](img%2Fapi-test.png)

- **reader 모듈 테스트 케이스**
![reader-test.png](img%2Freader-test.png)

- **core 모듈 테스트 케이스**
![core-test.png](img%2Fcore-test.png)


## 빌드 및 실행

### 빌드
``` shell
 ./gradlew clean :blog-api:buildNeeded   
```


## API 테스트
### 키워드 검색 API
- 요청 curl
```shell
curl --request GET 'http://localhost:8080/v1/documents' \
--header 'Content-Type: application/json' \
--data '{
    "query" : "사과",
    "sort" : "accuracy",
    "page" : 1,
    "size" : 10
}' | json_pp -json_opt pretty,utf8
```

- 응답
```json
{
   "documents" : [
      {
         "blogName" : "ks3569님의 블로그",
         "contents" : "그러던 중 아누카<b>사과</b> 추출분말이 들어있는 아누카리치 샴푸라는 것을 알게 되었어요. 이번에 생전... 이 안에 들어 있는 주성분인 아누카<b>사과</b> 추출문말이 탈모예방에 도움이 된다고 하더라구요. 저 역시도... ",
         "registrationDate" : "2023-06-04T21:00:00+09:00",
         "thumbnail" : null,
         "title" : "아누카<b>사과</b> 추출분말로 모발관리 도움받기",
         "url" : "https://blog.naver.com/ks3569/223119710170"
      },
      ... 생략
   ],
   "meta" : {
      "totalCount" : 8811803
   }
}

```


## 인기 검색어 Top10 조회

- 요청 curl
``` shell
curl --request GET 'http://localhost:8080/v1/keywords/popularTop10' \
--header 'Content-Type: application/json' \
--data '{
    "query" : "사과",
    "sort" : "accuracy",
    "page" : 50,
    "size" : 1
}' | json_pp -json_opt pretty,utf8
```

- 응답
```json
{
   "keywords" : [
      {
         "keyword" : "사과",
         "searchCount" : 7
      },
      {
         "keyword" : "배",
         "searchCount" : 1
      }
   ]
}

```