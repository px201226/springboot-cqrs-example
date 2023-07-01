package com.blogsearch.blogcore.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter @Builder
@Entity
@Table(name = "KEYWORD_ANALYTICS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KeywordAnalytics {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "KEYWORD", nullable = false)
	private String keyword;

	@Column(name = "SEARCH_COUNT", nullable = false)
	private Long searchCount;

	@Column(name = "CREATE_AT", nullable = false)
	private LocalDate createAt;

	public static KeywordAnalytics createNew(final String keyword) {
		return KeywordAnalytics.builder()
				.keyword(keyword)
				.searchCount(1L)
				.createAt(LocalDate.now())
				.build();
	}

	public void increase() {
		searchCount++;
	}
}
