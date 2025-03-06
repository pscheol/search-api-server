package com.search.adapter.infra.kakao.feign.dto

data class KakaoErrorResponse(
    val errorType: String = "",
    val message: String = ""
)
