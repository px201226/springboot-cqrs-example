package com.blogsearch.blogcore.domain;

import java.util.Optional;

public interface KeywordAnalyticsRepository {

	Optional<KeywordAnalytics> findByKeyword(String keyword);

	void save(KeywordAnalytics keywordAnalytics);

}
