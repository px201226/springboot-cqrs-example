package com.blogsearch.blogcore.domain;

import com.blogsearch.event.RetrievedKeywordEvent;

/**
 * 키워드 관련 이벤트가 발생했을 때, 실행되는 로직을 정의하는 인터페이스
 */
public interface KeywordEventDispatcher {

	/**
	 * 블로그 문서 키워드 조회 이벤트가 발행됐을 때, 실행되는 로직을 정의합니다
	 * @param event 키워드 조회 이벤트
	 */
	 void whenRetrievedKeywordOnBlog(RetrievedKeywordEvent event);


}
