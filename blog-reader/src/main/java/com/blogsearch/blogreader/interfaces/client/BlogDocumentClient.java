package com.blogsearch.blogreader.interfaces.client;

import com.blogsearch.blogreader.dto.BlogDocumentModel;
import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentResponse;

public interface BlogDocumentClient {

	SearchDocumentResponse findDocumentBy(BlogDocumentModel.SearchDocumentRequest request);
}
