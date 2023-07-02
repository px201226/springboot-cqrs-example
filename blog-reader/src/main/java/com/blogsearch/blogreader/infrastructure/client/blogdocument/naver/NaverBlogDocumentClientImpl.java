package com.blogsearch.blogreader.infrastructure.client.blogdocument.naver;

import com.blogsearch.blogreader.dto.BlogDocumentModel.Meta;
import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentRequest;
import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentResponse;
import com.blogsearch.blogreader.infrastructure.client.blogdocument.naver.NaverModel.Item;
import com.blogsearch.blogreader.infrastructure.client.blogdocument.naver.NaverModel.Sort;
import com.blogsearch.blogreader.interfaces.client.BlogDocumentClient;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverBlogDocumentClientImpl implements BlogDocumentClient {

	private final NaverFeignClient naverFeignClient;

	@Value("${external.api.naver.authorization.client-id}")
	private String clientId;

	@Value("${external.api.naver.authorization.client-secret}")
	private String clientSecret;

	@Override public SearchDocumentResponse findDocumentBy(final SearchDocumentRequest request) {

		final var naverResponse = naverFeignClient.searchBlog(
				clientId,
				clientSecret,
				request.getQuery(),
				Sort.from(request.getSort()),
				request.getPage(),
				request.getSize()
		);

		return SearchDocumentResponse.of(
				Meta.of(naverResponse.getTotal(), null),
				naverResponse.getItems().stream().map(Item::toBlogDocumentModel).collect(Collectors.toList())
		);

	}
}
