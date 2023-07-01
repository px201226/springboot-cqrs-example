package com.blogsearch.blogcore.domain;

import java.util.Optional;
import java.util.stream.Stream;

public interface KeywordAnalyticsRepository {

	Optional<KeywordAnalytics> findByKeyword(String keyword);

	KeywordAnalytics save(KeywordAnalytics keywordAnalytics);

	Stream<KeywordAnalytics> findAllStream();

}
