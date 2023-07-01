package com.blogsearch.blogcore.springboot.infrastructure.eventdispatcher;

import com.blogsearch.blogcore.domain.KeywordEventDispatcher;
import com.blogsearch.event.RetrievedKeywordEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RdbKeywordEventDispatcher implements KeywordEventDispatcher {

	@Override public void whenRetrievedKeyword(final RetrievedKeywordEvent event) {

		log.info("RDB event dispatcher");
	}
}
