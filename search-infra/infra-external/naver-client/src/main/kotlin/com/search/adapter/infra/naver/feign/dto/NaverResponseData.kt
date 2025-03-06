package com.search.adapter.infra.naver.feign.dto

import com.fasterxml.jackson.annotation.JsonProperty

sealed class NaverResponseData {
    data class BlogItem(
        val title: String = "",
        val link: String = "",
        val description: String = "",
        @JsonProperty("bloggername")
        val bloggerName: String = "",
        val postdate: String = "",
    ) : NaverResponseData()

    data class BookItem(
        val title: String = "",
        val link: String = "",
        val image: String = "",
        val author: String = "",
        val discount: Int = 0,
        val publisher: String = "",
        val isbn: String = "",
        val description: String = "",
        val pubdate: String = "",
        ) : NaverResponseData()
}
