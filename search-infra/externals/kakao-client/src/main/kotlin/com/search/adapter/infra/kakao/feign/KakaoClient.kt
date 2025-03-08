package com.search.adapter.infra.kakao.feign

import com.search.adapter.infra.kakao.feign.config.KakaoClientConfiguration
import com.search.adapter.infra.kakao.feign.dto.KakaoBlogResponse
import com.search.adapter.infra.kakao.feign.dto.KakaoBookResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "kakaoClient", url= "\${external.kakao.url}", configuration = [KakaoClientConfiguration::class])
interface KakaoClient {

    @GetMapping("/v3/search/book")
    fun searchBook(@RequestParam("query") query: String,
                   @RequestParam("page") page: Int,
                   @RequestParam("size") size: Int,
                   @RequestParam(name = "sort", defaultValue = "accuracy") sort: String = "accuracy",
    ): KakaoBookResponse

    @GetMapping("/v2/search/blog")
    fun searchBlog(@RequestParam("query") query: String,
                   @RequestParam("page") page: Int,
                   @RequestParam("size") size: Int,
                   @RequestParam(name = "sort", defaultValue = "accuracy") sort: String = "accuracy",
    ): KakaoBlogResponse
}