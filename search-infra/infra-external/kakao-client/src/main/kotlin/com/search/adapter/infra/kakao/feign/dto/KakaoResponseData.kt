package com.search.adapter.infra.kakao.feign.dto

import com.fasterxml.jackson.annotation.JsonProperty

sealed class KakaoResponseData {
    data class Meta(
        @JsonProperty("total_count")
        val totalCount: Int = 0,
        @JsonProperty("pageable_count")
        val pageableCount: Int = 0,
        @JsonProperty("is_end")
        val isEnd: Boolean = true,
    ) : KakaoResponseData()

    data class BlogDocument(
        @JsonProperty("blogname")
        val blogName: String = "",
        val contents: String = "",
        val datetime: String = "",
        val title: String = "",
        val url: String = "",
    ) : KakaoResponseData()

    data class BookDocument(
        val title: String = "",
        val contents: String = "",
        val url: String = "",
        val authors: List<String> = listOf(),
        val datetime: String = "",
        val isbn: String = "",
        val price: Int = 0,
        val publisher: String = "",
        @JsonProperty("sale_price")
        val salePrice: Int = 0,
        val status: String = "",
        val thumbnail: String = "",

        ) : KakaoResponseData()
}
