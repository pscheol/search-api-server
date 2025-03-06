package com.search.common.exception

import org.springframework.http.HttpStatus

class SearchAPIException(
    message: String,
    val errorType: ErrorType = ErrorType.INVALID_PARAMETER,
    val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
) : RuntimeException(message)