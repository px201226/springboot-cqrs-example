package com.blogsearch.blogcore.springboot.infrastructure.repository;

import com.blogsearch.blogcore.domain.KeywordAnalytics;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface KeywordAnalyticsJpaRepository extends JpaRepository<KeywordAnalytics, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<KeywordAnalytics> findByKeyword(String keyword);

}
