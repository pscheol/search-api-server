package com.search.adapter.infra.kakao.feign.dto

data class KakaoBookResponse(
    val meta: KakaoResponseData.Meta,
    val documents: List<KakaoResponseData.BookDocument>
)
