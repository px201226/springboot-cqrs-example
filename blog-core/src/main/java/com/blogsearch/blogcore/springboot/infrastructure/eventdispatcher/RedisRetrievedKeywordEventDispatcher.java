package com.blogsearch.blogcore.springboot.infrastructure.eventdispatcher;

import com.blogsearch.blogcore.domain.KeywordEventDispatcher;
import com.blogsearch.blogcore.springboot.infrastructure.repository.RedisKeywordAnalyticsRepository;
import com.blogsearch.event.RetrievedKeywordEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 블로그 키워드 검색 이벤트가 발생하면 Redis에 조회수를 업데이트합니다
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RedisRetrievedKeywordEventDispatcher implements KeywordEventDispatcher {

	private final RedisKeywordAnalyticsRepository redisRepository;
	@Override
	public void whenRetrievedKeywordOnBlog(final RetrievedKeywordEvent event) {

		log.info("Redis event dispatcher");
		redisRepository.increaseSearchCount(event.getKeyword());
	}
}
