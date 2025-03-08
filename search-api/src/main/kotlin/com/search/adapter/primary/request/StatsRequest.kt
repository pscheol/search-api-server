package com.search.adapter.primary.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class StatsRequest(
    @field:NotNull(message = "검색 타입이 누락되었습니다.]")
    @field:Pattern(regexp = "^(BOOK|BLOG)$", message = "잘못된 검색타입 입니다.[BOOK, BLOG]")
    @field:Schema(
        description = "검색 타입(BOOK|BLOG)",
        example = "BLOG",
        requiredMode = Schema.RequiredMode.REQUIRED,
    )
    val type: String,

    @field:NotBlank(message = "입력이 누락되었습니다.")
    @field:Size(max = 50, message = "query는 최대 50자를 초과할 수 없습니다.")
    @field:Schema(
        description = "검색쿼리",
        example = "Kotlin",
        requiredMode = Schema.RequiredMode.REQUIRED,
        maxLength = 50
    )
    val query: String,

    @field:NotNull(message = "검색 일자가 누락되었습니다.")
    @field:Schema(
        description = "검색일자",
        example = "2025-03-03",
        requiredMode = Schema.RequiredMode.REQUIRED,
    )
    val date: LocalDate,
)
