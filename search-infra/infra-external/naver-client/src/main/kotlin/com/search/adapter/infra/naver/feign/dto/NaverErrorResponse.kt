package com.search.adapter.infra.naver.feign.dto

data class NaverErrorResponse(
    val errorCode: String = "",
    val errorMessage: String = ""
)
