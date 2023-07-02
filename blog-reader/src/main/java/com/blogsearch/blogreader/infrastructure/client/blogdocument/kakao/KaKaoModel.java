package com.blogsearch.blogreader.infrastructure.client.blogdocument.kakao;

import com.blogsearch.blogreader.dto.BlogDocumentModel;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KaKaoModel {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor(staticName = "of")
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Response<T> {

		private Meta meta;
		private List<T> documents;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor(staticName = "of")
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Meta {

		/** 검색된 문서 수*/
		private Integer totalCount;

		/** 현재 페이지가 마지막 페이지인지 여부.*/
		private Boolean isEnd;

		public BlogDocumentModel.Meta toBlogDocumentModel() {
			return BlogDocumentModel.Meta.of(totalCount, isEnd);
		}
	}


	@Getter
	@NoArgsConstructor
	@AllArgsConstructor(staticName = "of")
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Document {

		private String title;
		private String contents;
		private String url;
		private String blogname;
		private String thumbnail;
		private OffsetDateTime datetime;

		public BlogDocumentModel.Document toBlogDocumentModel() {
			return BlogDocumentModel.Document.of(
					title,
					contents,
					url,
					blogname,
					thumbnail,
					datetime
			);
		}
	}
}
