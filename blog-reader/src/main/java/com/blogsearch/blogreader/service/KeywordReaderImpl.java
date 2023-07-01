package com.blogsearch.blogreader.service;

import com.blogsearch.blogreader.dto.KeywordModel.PopularKeywordsResponse;
import com.blogsearch.blogreader.interfaces.KeywordReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KeywordReaderImpl implements KeywordReader {


	@Override public PopularKeywordsResponse findPopularKeywords(final Integer limit) {

		return null;
	}
}
