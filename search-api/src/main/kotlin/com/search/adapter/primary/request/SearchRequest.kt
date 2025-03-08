package com.search.adapter.primary.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*

data class SearchRequest(
    @field:NotBlank(message = "입력이 누락되었습니다.")
    @field:Size(max = 50, message = "query는 최대 50자를 초과할 수 없습니다.")
    @field:Schema(description = "검색쿼리", example = "Kotlin", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 50)
    val query: String,

    @field:NotNull(message = "페이지 번호가 누락되었습니다.")
    @field:Min(value = 1, message = "페이지번호는 1이상이어야 합니다.")
    @field:Max(value = 10000, message = "페이지번호는 10000이하여야 합니다.")
    @field:Schema(
        description = "페이지 번호",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED,
        minLength = 1,
        maxLength = 10000
    )
    val page: Int,

    @field:NotNull(message = "페이지 사이즈가 누락되었습니다.")
    @field:Min(value = 1, message = "페이지크기는 1이상이어야 합니다.")
    @field:Max(value = 50, message = "페이지크기는 50이하여야 합니다.")
    @field:Schema(
        description = "페이지 사이즈",
        example = "10",
        requiredMode = Schema.RequiredMode.REQUIRED,
        minLength = 1,
        maxLength = 50
    )
    val size: Int,
)
