package com.blogsearch.blogapi.controller;

import com.blogsearch.blogreader.dto.BlogDocumentModel;
import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentResponse;
import com.blogsearch.blogreader.interfaces.BlogDocumentReader;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BlogDocumentController {

	private final BlogDocumentReader blogDocumentReader;

	/**
	 * 블로그 문서 조회 API
	 * @param request
	 * @return
	 */
	@GetMapping("/v1/documents")
	public BlogDocumentModel.SearchDocumentResponse getDocuments(
			final BlogDocumentModel.SearchDocumentRequest request
	) {
		final var documents = blogDocumentReader.findDocuments(request);

		return documents;
	}

}
