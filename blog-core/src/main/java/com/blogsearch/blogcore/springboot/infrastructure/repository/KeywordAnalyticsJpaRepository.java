package com.blogsearch.blogcore.springboot.infrastructure.repository;

import com.blogsearch.blogcore.domain.KeywordAnalytics;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordAnalyticsJpaRepository extends JpaRepository<KeywordAnalytics, Long> {

	Optional<KeywordAnalytics> findByKeyword(String keyword);

}
