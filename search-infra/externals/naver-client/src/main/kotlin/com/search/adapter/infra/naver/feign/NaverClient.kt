package com.search.adapter.infra.naver.feign

import com.search.adapter.infra.naver.feign.config.NaverClientConfiguration
import com.search.adapter.infra.naver.feign.dto.NaverBlogResponse
import com.search.adapter.infra.naver.feign.dto.NaverBookResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "naverClient", url = "\${external.naver.url}", configuration = [NaverClientConfiguration::class])
interface NaverClient {

    @GetMapping("/v1/search/book.json")
    fun searchBook(
        @RequestParam("query") query: String?,
        @RequestParam("start") start: Int,
        @RequestParam("display") display: Int,
        @RequestParam("sort") sort: String = "sim",
    ): NaverBookResponse


    @GetMapping("/v1/search/blog.json")
    fun searchBlog(
        @RequestParam("query") query: String?,
        @RequestParam("start") start: Int,
        @RequestParam("display") display: Int,
        @RequestParam("sort") sort: String = "sim",
    ): NaverBlogResponse

}