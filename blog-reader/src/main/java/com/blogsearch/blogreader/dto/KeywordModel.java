package com.blogsearch.blogreader.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KeywordModel {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class PopularKeywordsResponse {

		List<Keyword> keywords;
	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class Keyword {

		private String keyword;
		private Long searchCount;
	}

}
