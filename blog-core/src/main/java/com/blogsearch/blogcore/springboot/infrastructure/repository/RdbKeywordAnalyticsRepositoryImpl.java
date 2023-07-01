package com.blogsearch.blogcore.springboot.infrastructure.repository;

import com.blogsearch.blogcore.domain.KeywordAnalytics;
import com.blogsearch.blogcore.domain.KeywordAnalyticsRepository;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RdbKeywordAnalyticsRepositoryImpl implements KeywordAnalyticsRepository {

	private final KeywordAnalyticsJpaRepository keywordAnalyticsJpaRepository;
	@Override public Optional<KeywordAnalytics> findByKeyword(final String keyword) {
		return keywordAnalyticsJpaRepository.findByKeyword(keyword);
	}

	@Override public KeywordAnalytics save(final KeywordAnalytics keywordAnalytics) {
		return keywordAnalyticsJpaRepository.save(keywordAnalytics);
	}

	@Override public Stream<KeywordAnalytics> findAllStream() {
		return keywordAnalyticsJpaRepository.findAllStream();
	}
}
