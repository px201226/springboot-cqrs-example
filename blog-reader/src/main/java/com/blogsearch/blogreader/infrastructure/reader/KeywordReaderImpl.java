package com.blogsearch.blogreader.infrastructure.reader;

import com.blogsearch.blogreader.dto.KeywordModel.PopularKeywordsResponse;
import com.blogsearch.blogreader.reader.KeywordReader;
import com.blogsearch.blogreader.repository.PopularKeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KeywordReaderImpl implements KeywordReader {

	private final PopularKeywordRepository popularKeywordRepository;

	@Override public PopularKeywordsResponse findPopularKeywords(final Integer limit) {

		return PopularKeywordsResponse.of(popularKeywordRepository.findPopularKeywords(limit));
	}
}
