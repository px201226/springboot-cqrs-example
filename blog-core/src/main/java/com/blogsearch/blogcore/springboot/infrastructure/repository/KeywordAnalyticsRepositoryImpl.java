package com.blogsearch.blogcore.springboot.infrastructure.repository;

import com.blogsearch.blogcore.domain.KeywordAnalytics;
import com.blogsearch.blogcore.domain.KeywordAnalyticsRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class KeywordAnalyticsRepositoryImpl implements KeywordAnalyticsRepository {

	private final KeywordAnalyticsJpaRepository keywordAnalyticsJpaRepository;
	@Override public Optional<KeywordAnalytics> findByKeyword(final String keyword) {
		return keywordAnalyticsJpaRepository.findByKeyword(keyword);
	}

	@Override public void save(final KeywordAnalytics keywordAnalytics) {
		keywordAnalyticsJpaRepository.save(keywordAnalytics);
	}

}
