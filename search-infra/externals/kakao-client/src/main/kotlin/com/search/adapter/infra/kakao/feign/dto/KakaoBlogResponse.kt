package com.search.adapter.infra.kakao.feign.dto

data class KakaoBlogResponse(
    val meta: KakaoResponseData.Meta,
    val documents: List<KakaoResponseData.BlogDocument>
)
