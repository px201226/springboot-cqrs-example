package com.blogsearch.blogreader.infrastructure.client.blogdocument.kakao;

import com.blogsearch.blogreader.dto.BlogDocumentModel;
import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentRequest;
import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentResponse;
import com.blogsearch.blogreader.infrastructure.client.blogdocument.kakao.KaKaoModel.Document;
import com.blogsearch.blogreader.interfaces.client.BlogDocumentClient;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoBlogDocumentClientImpl implements BlogDocumentClient {

	private final KaKaoFeignClient kaKaoFeignClient;

	@Value("${external.api.kakao.authorization.type}" + " " + "${external.api.kakao.authorization.credentials}")
	private String authorization;

	@Override public SearchDocumentResponse findDocumentBy(final SearchDocumentRequest request) {

		final var kakaoResponse = kaKaoFeignClient.searchBlog(
				authorization,
				request.getQuery(),
				request.getSort().name(),
				request.getPage(),
				request.getSize()
		);

		return BlogDocumentModel.SearchDocumentResponse.of(
				kakaoResponse.getMeta().toBlogDocumentModel(),
				kakaoResponse.getDocuments().stream().map(Document::toBlogDocumentModel).collect(Collectors.toList())
		);

	}


}
