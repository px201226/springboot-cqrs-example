package com.blogsearch.blogcore.domain;


import com.blogsearch.event.RetrievedKeywordEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RetrievedKeywordEventListener {

	private final List<KeywordEventDispatcher> eventDispatchers;

	@EventListener
	public void whenRetrievedKeyword(RetrievedKeywordEvent event) {
		log.info("whenRetrievedKeyword keyword = {}", event.getKeyword());

		eventDispatchers.forEach(dispatch -> dispatch.whenRetrievedKeyword(event));

	}
}
