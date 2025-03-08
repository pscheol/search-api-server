package com.search.adapter.infra.naver.feign.dto

data class NaverBlogResponse(
    val lastBuildDate: String = "",
    val total: Int = 0,
    val start: Int = 0,
    val display: Int = 0,
    val items: List<NaverResponseData.BlogItem> = listOf()
)