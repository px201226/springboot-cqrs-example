package com.blogsearch.blogreader.infrastructure.reader;

import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentRequest;
import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentResponse;
import com.blogsearch.blogreader.interfaces.client.BlogDocumentClient;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
class BlogDocumentReaderImplTest {

	static class MockEventPublisher implements ApplicationEventPublisher {

		public Integer callCount = 0;

		@Override public void publishEvent(final Object event) {
			callCount++;
		}
	}

	static class SuccessBlogDocumentClient implements BlogDocumentClient {

		@Override public SearchDocumentResponse findDocumentBy(final SearchDocumentRequest request) {
			return SearchDocumentResponse.of(null, null);
		}
	}

	static class FailBlogDocumentClient implements BlogDocumentClient {

		@Override public SearchDocumentResponse findDocumentBy(final SearchDocumentRequest request) {
			throw new RuntimeException();
		}
	}


	@Nested
	@DisplayName("findDocumentBy 로 블로그 문서를 검색했을 때")
	class FindDocument {

		@Test
		@DisplayName("이용가능한 BlogDocumentClient 가 있으면, SearchDocumentResponse 응답을 받는다 ")
		void 이용가능한_BlogDocumentClient_가_있으면_SearchDocumentResponse_응답을_받는다() {

			// given
			final var blogDocumentClients = List.of(new SuccessBlogDocumentClient(), new FailBlogDocumentClient());
			final var blogDocumentReader = new BlogDocumentReaderImpl(blogDocumentClients, new MockEventPublisher());
			final var request = SearchDocumentRequest.of(null, null, null, null);

			// when
			final var documents = blogDocumentReader.findDocuments(request);

			// then
			Assertions.assertNotNull(documents);
		}

		@Test
		@DisplayName("모든 BlogDocumentClient 가 이용불가능 하다면, Exception이 발생한다")
		void 모든_BlogDocumentClient_가_이용불가능_하다면_Exception이_발생한다() {

			// given
			final var blogDocumentClients = List.<BlogDocumentClient>of(new FailBlogDocumentClient(), new FailBlogDocumentClient());
			final var blogDocumentReader = new BlogDocumentReaderImpl(blogDocumentClients, new MockEventPublisher());
			final var request = SearchDocumentRequest.of(null, null, null, null);

			// when && then
			Assertions.assertThrows(
					RuntimeException.class,
					() -> blogDocumentReader.findDocuments(request)
			);
		}

		@Test
		@DisplayName("첫번째 BlogDocumentClient 가 이용불가능하고, 두번째 BlogDocumentClient가 이용가능하다면, fallback이 실행된다")
		void 첫번째_BlogDocumentClient_가_이용불가능하고_두번째_BlogDocumentClient가_이용가능하다면_fallback이_실행된다() {

			// given
			final var blogDocumentClients = List.<BlogDocumentClient>of(new FailBlogDocumentClient(), new SuccessBlogDocumentClient());
			final var blogDocumentReader = new BlogDocumentReaderImpl(blogDocumentClients, new MockEventPublisher());
			final var request = SearchDocumentRequest.of(null, null, null, null);

			// when
			final var documents = blogDocumentReader.findDocuments(request);

			// then
			Assertions.assertNotNull(documents);
		}

		@Test
		@DisplayName("이벤트 발행자로 이벤트가 발행된다")
		void 이벤트_발행자로_이벤트가_발행된다() {

			// given
			final var blogDocumentClients = List.<BlogDocumentClient>of(new FailBlogDocumentClient(), new SuccessBlogDocumentClient());
			final var mockEventPublisher = new MockEventPublisher();
			final var blogDocumentReader = new BlogDocumentReaderImpl(blogDocumentClients, mockEventPublisher);
			final var request = SearchDocumentRequest.of(null, null, null, null);

			// when
			final var documents = blogDocumentReader.findDocuments(request);

			// then
			Assertions.assertEquals(1, mockEventPublisher.callCount);

		}


	}


}