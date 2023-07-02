package com.blogsearch.blogreader.infrastructure.reader;

import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentRequest;
import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentResponse;
import com.blogsearch.blogreader.interfaces.client.BlogDocumentClient;
import com.blogsearch.blogreader.interfaces.reader.BlogDocumentReader;
import com.blogsearch.event.RetrievedKeywordEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BlogDocumentReaderImpl implements BlogDocumentReader {

	private final List<BlogDocumentClient> blogDocumentClients;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	public SearchDocumentResponse findDocuments(final SearchDocumentRequest request) {

		for (final BlogDocumentClient client : blogDocumentClients) {
			try {

				final var documentResponse = client.findDocumentBy(request);

				eventPublisher.publishEvent(RetrievedKeywordEvent.of(request.getQuery()));
				return documentResponse;

			} catch (Exception e) {
				log.error("occurred exception {}", client.getClass().getSimpleName(), e);
			}
		}

		throw new RuntimeException("검색 실패");
	}
}
