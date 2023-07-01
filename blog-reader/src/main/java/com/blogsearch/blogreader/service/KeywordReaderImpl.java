package com.blogsearch.blogreader.service;

import com.blogsearch.blogreader.dto.KeywordModel.PopularKeywordsResponse;
import com.blogsearch.blogreader.interfaces.KeywordReader;
import com.blogsearch.event.RetrievedPopularKeywordEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KeywordReaderImpl implements KeywordReader {

	private final ApplicationEventPublisher eventPublisher;

	@Override public PopularKeywordsResponse findPopularKeywords(final Integer limit) {

		eventPublisher.publishEvent(new RetrievedPopularKeywordEvent());
		return null;
	}
}
