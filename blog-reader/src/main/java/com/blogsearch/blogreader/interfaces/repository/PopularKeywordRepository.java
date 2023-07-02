package com.blogsearch.blogreader.interfaces.repository;

import com.blogsearch.blogreader.dto.KeywordModel.Keyword;
import java.util.List;

public interface PopularKeywordRepository {

	List<Keyword> findPopularKeywords(Integer limit);
}
