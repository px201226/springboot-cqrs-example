package com.blogsearch.blogcore.springboot.infrastructure.eventdispatcher;

import com.blogsearch.blogcore.domain.KeywordAnalytics;
import com.blogsearch.blogcore.domain.KeywordAnalyticsRepository;
import com.blogsearch.blogcore.domain.KeywordEventDispatcher;
import com.blogsearch.event.RetrievedKeywordEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 블로그 키워드 검색 이벤트가 발생하면 RDB에 조회수를 업데이트합니다
 */
@Slf4j
@RequiredArgsConstructor
@Component

public class KeywordEventAnalyticsDispatcher implements KeywordEventDispatcher {

	private final KeywordAnalyticsRepository keywordAnalyticsRepository;

	@Transactional
	@Override
	public void whenRetrievedKeywordOnBlog(final RetrievedKeywordEvent event) {

		log.info("RDB event dispatcher");
		keywordAnalyticsRepository
				.findByKeyword(event.getKeyword())
				.ifPresentOrElse(
						KeywordAnalytics::increase,
						() -> keywordAnalyticsRepository.save(KeywordAnalytics.createNew(event.getKeyword()))
				);

	}
}
