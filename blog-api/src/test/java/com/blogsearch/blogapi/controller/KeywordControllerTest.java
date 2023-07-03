package com.blogsearch.blogapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class KeywordControllerTest {

	@Autowired MockMvc mockMvc;
	@Autowired ObjectMapper objectMapper;

	@Test
	@DisplayName("인기 검색어 탑10 를 조회할 수 있다")
	void SearchDocumentRequest로_블로그_문서를_검색할_수_있다() throws Exception {

		mockMvc.perform(
						get("/v1/keywords/popularTop10")
				)
				.andExpect(status().isOk())
				.andDo(print());

	}

}