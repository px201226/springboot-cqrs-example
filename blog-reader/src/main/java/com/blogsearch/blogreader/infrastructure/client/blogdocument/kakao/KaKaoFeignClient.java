package com.blogsearch.blogreader.infrastructure.client.blogdocument.kakao;

import com.blogsearch.blogreader.infrastructure.client.blogdocument.kakao.KaKaoModel.Document;
import com.blogsearch.blogreader.infrastructure.client.blogdocument.kakao.KaKaoModel.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "KaKaoFeignClient", url = "${external.api.kakao.url}")
public interface KaKaoFeignClient {

    // 블로그 검색 오픈 API
    @GetMapping(value = "/v2/search/blog")
    Response<Document> searchBlog(@RequestHeader(value = "Authorization") String authorization,
                                       @RequestParam(value = "query") String query,
                                       @RequestParam(value = "sort", required = false) String sort,
                                       @RequestParam(value = "page", required = false) int page,
                                       @RequestParam(value = "size", required = false) int size);
}
