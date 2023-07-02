package com.blogsearch.blogcore.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.blogsearch.event.RetrievedKeywordEvent;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("키워드 조회 이벤트 Listener 테스트")
class RetrievedKeywordEventListenerTest {


	static class MockEventDispatcher implements KeywordEventDispatcher {

		public int callCount = 0;

		@Override public void whenRetrievedKeywordOnBlog(final RetrievedKeywordEvent event) {
			callCount++;
		}
	}

	@Test
	@DisplayName("키워드 조회 이벤트를 리스닝하면, 이벤트 디스패처 구현체를 모두 호출한다")
	void 키워드_조회_이벤트를_리스닝하면_이벤트_디스패처_구현체를_모두_호출한다() {

		// given
		final List<KeywordEventDispatcher> mockEventDispatchers = List.of(
				new MockEventDispatcher(),
				new MockEventDispatcher()
		);

		final var retrievedKeywordEventListener = new RetrievedKeywordEventListener(mockEventDispatchers);

		final var event = RetrievedKeywordEvent.of("keyword");

		// when
		retrievedKeywordEventListener.whenRetrievedKeyword(event);

		// then
		mockEventDispatchers.stream()
				.map(MockEventDispatcher.class::cast)
				.forEach(mockEventDispatcher -> Assertions.assertEquals(1, mockEventDispatcher.callCount));


	}

}