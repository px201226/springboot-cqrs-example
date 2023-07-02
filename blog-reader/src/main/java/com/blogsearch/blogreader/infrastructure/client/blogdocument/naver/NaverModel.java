package com.blogsearch.blogreader.infrastructure.client.blogdocument.naver;

import com.blogsearch.blogreader.dto.BlogDocumentModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverModel {


	@Getter
	@NoArgsConstructor
	@AllArgsConstructor(staticName = "of")
	public static class NaverResponse<T> {

		private Integer total;
		private List<T> items;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor(staticName = "of")
	public static class Item {

		private String title;

		private String description;

		private String link;

		private String bloggername;

		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
		private LocalDate postdate;

		public BlogDocumentModel.Document toBlogDocumentModel() {
			return BlogDocumentModel.Document.of(
					title,
					description,
					link,
					bloggername,
					null,
					OffsetDateTime.of(postdate, LocalTime.NOON, ZoneOffset.UTC)
			);
		}
	}

	public enum Sort {

		/**
		 * 정확도순 내림차순
		 */
		sim,

		/**
		 * 날짜순 내림차순
		 */
		date;

		public static Sort from(final BlogDocumentModel.Sort sort) {
			if (BlogDocumentModel.Sort.accuracy.equals(sort)) {
				return sim;
			} else {
				return date;
			}
		}
	}
}
