package com.blogsearch.blogreader.infrastructure.repository;

import com.blogsearch.blogreader.dto.KeywordModel.Keyword;
import com.blogsearch.blogreader.interfaces.repository.PopularKeywordRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PopularKeywordRepositoryImpl implements PopularKeywordRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override public List<Keyword> findPopularKeywords(final Integer limit) {

		final var sql = "SELECT keyword, search_count FROM keyword_analytics k ORDER BY search_count DESC LIMIT (:limit)";

		final var parameters = new MapSqlParameterSource("limit", limit);

		RowMapper<Keyword> rowMapper = (rs, count) -> Keyword.of(
				rs.getString("keyword"),
				rs.getLong("search_count")
		);

		return jdbcTemplate.query(sql, parameters, rowMapper);
	}
}
