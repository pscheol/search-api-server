package com.search.adapter.primary.response

import com.search.exception.ErrorType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Error Response")
@JvmRecord
data class ErrorResponse(
    @Schema(description = "에러 메세지", example = "Invalid parameters")
    val errorMessage: String,

    @Schema(description = "에러 타입", example = "INVALID_PARAMETER")
    val errorType: ErrorType
)
