package com.blogsearch.blogcore.springboot.infrastructure.repository;

import com.blogsearch.blogcore.domain.KeywordAnalytics;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KeywordAnalyticsJpaRepository extends JpaRepository<KeywordAnalytics, Long> {

	Optional<KeywordAnalytics> findByKeyword(String keyword);

	@Query("select keywordAnalytics from KeywordAnalytics keywordAnalytics")
	Stream<KeywordAnalytics> findAllStream();
}
