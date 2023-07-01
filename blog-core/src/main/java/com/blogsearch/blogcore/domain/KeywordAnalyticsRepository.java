package com.blogsearch.blogcore.domain;

import java.util.Optional;

public interface KeywordAnalyticsRepository {

	Optional<KeywordAnalytics> findByKeyword(String keyword);

	KeywordAnalytics save(KeywordAnalytics keywordAnalytics);

}
