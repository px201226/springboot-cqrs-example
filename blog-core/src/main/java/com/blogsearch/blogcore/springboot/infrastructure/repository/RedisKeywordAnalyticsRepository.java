package com.blogsearch.blogcore.springboot.infrastructure.repository;

import com.blogsearch.blogcore.domain.KeywordAnalytics;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisKeywordAnalyticsRepository {

	private static final String REDIS_KEYWORD_ANALYTICS_KEY = "KeywordAnalytics:searchCount";
	private static final String REDIS_IS_CACHEABLE_SEARCH_COUNT_KEY = "KeywordAnalytics:searchCount:isCacheable";

	private static final RedisSerializer<String> keySerializer = new StringRedisSerializer();

	private final RedisTemplate<String, String> redisTemplate;

	/**
	 * ZINCRBY key increment member
	 * 지정한 만큼 score 증가, 감소
	 *
	 * Note: redis에 해당 key 나 member 로 값이 없더라도 값을 생성 후, 1로 만든다.
	 * @param keyword
	 */
	public void increaseSearchCount(final String keyword) {
		zSetOpt().incrementScore(REDIS_KEYWORD_ANALYTICS_KEY, keyword, 1);
	}

	/**
	 * Keyword의 SearchCount가 캐쉬되어 있는지 확인합니다.
	 * 레디스 재부팅 등의 이유로 휘발될 수 있습니다.
	 * @return
	 */
	public Boolean isCacheableSearchCount() {
		final var isCacheable = valueOpt().get(REDIS_IS_CACHEABLE_SEARCH_COUNT_KEY);

		return isCacheable != null && isCacheable.toUpperCase().equals(Boolean.TRUE.toString());

	}

	public void saveAll(Iterable<KeywordAnalytics> keywordAnalytics) {

		redisTemplate.executePipelined((RedisCallback<Object>) connection ->
				{
					keywordAnalytics.forEach(entity -> {

						connection.zAdd(
								Objects.requireNonNull(keySerializer.serialize(REDIS_KEYWORD_ANALYTICS_KEY)),
								entity.getSearchCount(),
								Objects.requireNonNull(keySerializer.serialize(entity.getKeyword()))
						);

					});
					return null;
				}
		);
	}

	private ZSetOperations<String, String> zSetOpt() {
		return redisTemplate.opsForZSet();
	}

	private ValueOperations<String, String> valueOpt() {
		return redisTemplate.opsForValue();
	}
}
