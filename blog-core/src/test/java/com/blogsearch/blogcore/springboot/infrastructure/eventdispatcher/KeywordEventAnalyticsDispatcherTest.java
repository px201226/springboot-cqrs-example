package com.blogsearch.blogcore.springboot.infrastructure.eventdispatcher;

import com.blogsearch.blogcore.domain.KeywordAnalytics;
import com.blogsearch.blogcore.springboot.infrastructure.repository.KeywordAnalyticsJpaRepository;
import com.blogsearch.event.RetrievedKeywordEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@DisplayName("키워드 조회 이벤트 Dispatcher 테스트")
class KeywordEventAnalyticsDispatcherTest {

	@Autowired KeywordEventAnalyticsDispatcher eventDispatcher;
	@Autowired KeywordAnalyticsJpaRepository keywordAnalyticsJpaRepository;

	@Nested
	@DisplayName("키워드 조회 이벤트가 발생했을 때,")
	class DispatcherTest {

		@Test
		@DisplayName("DB에 해당 키워드가 저장되어 있지 않으면, 조회수 1로 저장한다")
		void 데이터베이스에_해당_키워드가_저장되어_있지_않으면_새로_저장한다() {

			// given
			final var keyword = "새로운키워드";
			final var expectSearchCount = 1;
			final var event = RetrievedKeywordEvent.of(keyword);

			// when
			eventDispatcher.whenRetrievedKeywordOnBlog(event);

			// then
			final var keywordAnalytics = keywordAnalyticsJpaRepository.findByKeyword(keyword).get();
			Assertions.assertNotNull(keywordAnalytics);
			Assertions.assertNotNull(keywordAnalytics.getId());
			Assertions.assertEquals(expectSearchCount, keywordAnalytics.getSearchCount());
		}

		@Test
		@DisplayName("DB에 해당 키워드가 이미 저장되어 있다면, 조회수를 1증가 시킨다.")
		void DB에_해당_키워드가_이미_저장되어_있다면_조회수를_1증가_시킨다() {

			// given
			final var givenKeyword = "존재하던키워드";
			final var givenSearchCount = 2L;
			final var expectSearchCount = givenSearchCount + 1L;
			keywordAnalyticsJpaRepository.save(
					KeywordAnalytics.builder()
							.keyword(givenKeyword)
							.searchCount(givenSearchCount)
							.createAt(LocalDate.now())
							.build()
			);

			final var event = RetrievedKeywordEvent.of(givenKeyword);

			// when
			eventDispatcher.whenRetrievedKeywordOnBlog(event);

			// then
			final var keywordAnalytics = keywordAnalyticsJpaRepository.findByKeyword(givenKeyword).get();
			Assertions.assertEquals(expectSearchCount, keywordAnalytics.getSearchCount());


		}

	}

	@Test
	@DisplayName("동시에 같은 키워드 조회수를 증가시켜도, 갱신손실이 발생하지 않는다")
	void 동시에_같은_키워드_조회수를_증가시켜도_갱신손실이_발생하지_않는다() throws InterruptedException {

		// given
		final var requestLimit = BigDecimal.valueOf(10);
		final var expectSearchCount = requestLimit;
		final var keyword = "myKeyword";

		// when
		final var countDownLatch = new CountDownLatch(requestLimit.intValue());
		Stream
				.generate(() -> new Thread(new Worker(countDownLatch, RetrievedKeywordEvent.of(keyword))))
				.limit(requestLimit.intValue())
				.forEach(Thread::start);

		countDownLatch.await(10, TimeUnit.SECONDS);

		// then
		final var keywordAnalytics = keywordAnalyticsJpaRepository.findByKeyword(keyword).get();
		Assertions.assertEquals(expectSearchCount.intValue(), keywordAnalytics.getSearchCount());
	}

	private class Worker implements Runnable {

		private final CountDownLatch countDownLatch;
		private final RetrievedKeywordEvent event;

		public Worker(final CountDownLatch countDownLatch, final RetrievedKeywordEvent event) {
			this.countDownLatch = countDownLatch;
			this.event = event;
		}

		@Override public void run() {
			eventDispatcher.whenRetrievedKeywordOnBlog(event);
			countDownLatch.countDown();
		}
	}

}