package com.blogsearch.blogreader.dto;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlogDocumentModel {

	/**
	 * 블로그 문서 검색 요청 DTO
	 */
	@Getter
	@Setter
	@ToString
	public static class SearchDocumentRequest {

		/** 검색을 원하는 질의어 */
		@NotNull @NotEmpty
		private String query;

		/** 결과 문서 정렬 방식 */
		private Sort sort;

		/** 결과 페이지 번호 */
		@Min(1) @Max(50)
		private Integer page;

		/** 한 페이지에 보여질 문서 수 */
		@Min(1) @Max(50)
		private Integer size;

	}


	/**
	 * 블로그 문서 검색 결과 DTO
	 */
	@Getter
	@ToString
	@AllArgsConstructor(staticName = "of")
	public static class SearchDocumentResponse {

		private Meta meta;
		private List<Document> documents;
	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class Meta {

		/** 검색된 문서 수*/
		private Integer totalCount;

		/** 현재 페이지가 마지막 페이지인지 여부.*/
		private Boolean isEnd;
	}



	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class Document {

		/** 블로그 글 제목 */
		private String title;

		/** 블로그 글 요약 */
		private String contents;

		/** 블로그 글 URL */
		private String url;

		/** 블로그의 이름 */
		private String blogName;

		/** 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음 */
		private String thumbnail;

		/** 블로그 글 작성일자 */
		private LocalDate registrationDate;

	}

	/**
	 * 결과 문서 정렬 방식
	 * */
	@Getter
	@AllArgsConstructor
	public enum Sort {

		/** 정확도순 */
		accuracy,
		/** 최신순 */
		recency;


	}
}
