package com.search.adapter.primary.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "검색 통계")
data class StatResponse(
    @Schema(description = "검색타입", example = "BLOG, BOOK")
    val type: String,
    @Schema(description = "쿼리", example = "HTTP")
    val query: String,
    @Schema(description = "검색횟수", example = "10")
    val count: Long,
)
