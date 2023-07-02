package com.blogsearch.blogreader.infrastructure.client.blogdocument.naver;

import com.blogsearch.blogreader.infrastructure.client.blogdocument.naver.NaverModel.Item;
import com.blogsearch.blogreader.infrastructure.client.blogdocument.naver.NaverModel.NaverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "NaverFeignClient", url = "${external.api.naver.url}")
public interface NaverFeignClient {

	@GetMapping("/v1/search/blog.json")
	NaverResponse<Item> searchBlog(
			@RequestHeader(value = "X-Naver-Client-Id") String id,
			@RequestHeader(value = "X-Naver-Client-Secret") String secret,
			@RequestParam(value = "query") String query,
			@RequestParam(value = "sort", required = false) NaverModel.Sort sort,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "display", required = false) Integer display
	);

}