package com.search.adapter.infra.naver.feign.dto

data class NaverBookResponse(
    val lastBuildDate: String = "",
    val total: Int = 0,
    val start: Int = 0,
    val display: Int = 0,
    val items: List<NaverResponseData.BookItem> = listOf()
)
