package com.blogsearch.blogreader.interfaces.reader;

import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentRequest;
import com.blogsearch.blogreader.dto.BlogDocumentModel.SearchDocumentResponse;

public interface BlogDocumentReader {

	SearchDocumentResponse findDocuments(SearchDocumentRequest searchDocumentRequest);
}
