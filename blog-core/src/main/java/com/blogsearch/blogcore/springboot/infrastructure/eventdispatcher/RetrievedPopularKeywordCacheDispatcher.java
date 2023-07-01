package com.blogsearch.blogcore.springboot.infrastructure.eventdispatcher;

import com.blogsearch.blogcore.domain.KeywordAnalyticsRepository;
import com.blogsearch.blogcore.domain.KeywordEventDispatcher;
import com.blogsearch.blogcore.springboot.infrastructure.repository.RedisKeywordAnalyticsRepository;
import com.blogsearch.event.RetrievedPopularKeywordEvent;
import com.blogsearch.util.BatchingIterator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 인기검색어 조회 시, Redis에 캐쉬되어 있지 않다면 RDB에서 Redis로 데이터를 캐쉬합니다.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RetrievedPopularKeywordCacheDispatcher implements KeywordEventDispatcher {

	private final KeywordAnalyticsRepository keywordAnalyticsRepository;
	private final RedisKeywordAnalyticsRepository redisKeywordAnalyticsRepository;

	@Transactional
	@Override
	public void whenRetrievedPopularKeyword(final RetrievedPopularKeywordEvent event) {

		if (redisKeywordAnalyticsRepository.isCacheableSearchCount()) {
			return;
		}

		BatchingIterator.batchedStreamOf(
						keywordAnalyticsRepository.findAllStream(),
						1000
				)
				.forEach(redisKeywordAnalyticsRepository::saveAll);


	}
}
