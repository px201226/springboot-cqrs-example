package com.blogsearch.blogreader.interfaces.reader;

import com.blogsearch.blogreader.dto.KeywordModel.PopularKeywordsResponse;

public interface KeywordReader {

	/**
	 * 인기 검색어를 조회합니다.
	 * @param limit 데이터 건수 제한
	 * @return
	 */
	PopularKeywordsResponse findPopularKeywords(Integer limit);
}
