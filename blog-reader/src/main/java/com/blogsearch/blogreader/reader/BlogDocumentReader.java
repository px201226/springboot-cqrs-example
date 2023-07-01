package com.blogsearch.blogreader.reader;

import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentRequest;
import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentResponse;

public interface BlogDocumentReader {

	SearchDocumentResponse findDocuments(SearchDocumentRequest searchDocumentRequest);
}
