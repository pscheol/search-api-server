package com.search.common.exception

enum class ErrorType(val message: String) {
    EXTERNAL_API_ERROR("External API Error"),
    UNKNOWN("Unknown error"),
    INVALID_PARAMETER("Invalid parameters"),
    NO_RESOURCE("No source found"),;
}