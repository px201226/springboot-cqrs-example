package com.blogsearch.blogreader.infrastructure.client.blogdocument.kakao;

import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentRequest;
import com.blogsearch.blogreader.dto.BlogDocumentModel.Sort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KakaoBlogDocumentClientImplTest {

	@Autowired KakaoBlogDocumentClientImpl kakaoBlogDocumentClient;

	@ParameterizedTest
	@EnumSource(value = Sort.class, names = {"accuracy", "recency"})
	@DisplayName("카카오 블로그 검색 REST API 호출 테스트")
	void 카카오_블로그_검색_REST_API_호출_테스트(Sort sort) {

		//given
		final var request = SearchDocumentRequest.of(
				"Spring",
				sort,
				1,
				10
		);

		// when
		final var response = kakaoBlogDocumentClient.findDocumentBy(request);

		// then
		Assertions.assertTrue(response.getMeta().getTotalCount() >= 0);


	}
}