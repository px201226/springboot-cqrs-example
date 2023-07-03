package com.blogsearch.blogreader.infrastructure.repository;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class PopularKeywordRepositoryImplTest {

	@Autowired PopularKeywordRepositoryImpl popularKeywordRepository;

	@Test
	@DisplayName("findPopularKeywords 호출했을 때, Limit 이하 만큼 데이터가 조회된다")
	@Sql("/keyword-setup.sql")
	void findPopularKeywords_호출했을_때_Limit_이하_만큼_데이터가_조회된다() {

		// given
		int limit = 5;

		// when
		final var popularKeywords = popularKeywordRepository.findPopularKeywords(limit);

		// then
		Assertions.assertEquals(limit, popularKeywords.size());

	}

	@Test
	@DisplayName("findPopularKeywords 호출했을 때, 조회수 내림차순으로 키워드가 조회된다")
	@Sql("/keyword-setup.sql")
	void findPopularKeywords_호출했을_때_데이터가_조회수_역순으로_조회된다() {

		// given
		boolean isDescending = true;

		// when
		final var popularKeywords = popularKeywordRepository.findPopularKeywords(10);

		for (int i = 0; i < popularKeywords.size() - 1; i++) {
			if (popularKeywords.get(i).getSearchCount() < popularKeywords.get(i + 1).getSearchCount()) {
				isDescending = false;
				break;
			}
		}

		// then
		Assertions.assertTrue(isDescending);
	}

	@Test
	@DisplayName("findPopularKeywords 호출했을 때, 조회수가 높은 순으로 키워드가 조회된다")
	@Sql("/keyword-setup.sql")
	void findPopularKeywords_호출했을_때_조회수가_높은_순으로_키워드가_조회된다() {

		// given
		final var expectKeywordOrdering = List.of("키워드16", "키워드15", "키워드14");

		// when
		final var popularKeywords = popularKeywordRepository.findPopularKeywords(expectKeywordOrdering.size());

		// then
		for (int i = 0; i < expectKeywordOrdering.size(); i++) {
			Assertions.assertEquals(expectKeywordOrdering.get(i), popularKeywords.get(i).getKeyword());
		}
	}
}