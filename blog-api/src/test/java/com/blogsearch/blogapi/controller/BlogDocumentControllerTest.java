package com.blogsearch.blogapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentRequest;
import com.blogsearch.blogreader.dto.BlogDocumentModel.Sort;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BlogDocumentControllerTest {

	@Autowired MockMvc mockMvc;
	@Autowired ObjectMapper objectMapper;

	@Test
	@DisplayName("SearchDocumentRequest로, 블로그 문서를 검색할 수 있다")
	void SearchDocumentRequest로_블로그_문서를_검색할_수_있다() throws Exception {

		final var request = SearchDocumentRequest.of("카카오", Sort.accuracy, 1, 10);

		mockMvc.perform(
						get("/v1/documents")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(request))

				)
				.andExpect(status().isOk())
				.andDo(print());

	}

	@ParameterizedTest
	@MethodSource("provideInvalidRequest")
	@DisplayName("SearchDocumentRequest 데이터에 유효성 에러가 있다면, BAD_REQUEST 에러가 반환된다")
	void SearchDocumentRequest_데이터에_유효성_에러가_있다면_BAD_REQUEST_에러가_반환된다(SearchDocumentRequest request) throws Exception {

		mockMvc.perform(
						get("/v1/documents")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(request))

				)
				.andExpect(status().isBadRequest())
				.andDo(print());

	}

	private static Stream<Arguments> provideInvalidRequest() {
		return Stream.of(
				Arguments.of(SearchDocumentRequest.of("카카오", Sort.accuracy, 51, 10)),
				Arguments.of(SearchDocumentRequest.of("", null, 1, 10)),
				Arguments.of(SearchDocumentRequest.of(null, Sort.accuracy, 1, 10))
		);
	}
}
