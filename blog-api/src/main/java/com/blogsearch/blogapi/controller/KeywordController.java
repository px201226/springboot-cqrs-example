package com.blogsearch.blogapi.controller;

import com.blogsearch.blogreader.dto.KeywordModel.PopularKeywordsResponse;
import com.blogsearch.blogreader.reader.KeywordReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class KeywordController {

	private final KeywordReader keywordReader;

	/**
	 * 인기 검색어 탑10 조회 API
	 * @return
	 */
	@GetMapping("/v1/keywords/popularTop10")
	public PopularKeywordsResponse retrievePopularKeywords() {
		return keywordReader.findPopularKeywords(10);
	}

}
