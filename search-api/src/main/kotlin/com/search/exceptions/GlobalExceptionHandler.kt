package com.search.exceptions

import com.search.adapter.primary.response.ErrorResponse
import com.search.exception.ErrorType
import com.search.exception.SearchAPIException
import com.search.logger.DefaultLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.resource.NoResourceFoundException


@RestControllerAdvice
class GlobalExceptionHandler : DefaultLogger {
    @ExceptionHandler(SearchAPIException::class)
    fun handleApiException(e: SearchAPIException): ResponseEntity<ErrorResponse> {
        log.error(e) { "Api Exception occurred. message=${e.message}, className=${e.javaClass.name}" }

        return ResponseEntity.status(e.httpStatus)
            .body(ErrorResponse(e.message?: "", e.errorType))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error(e) { "Exception Exception occurred. message=${e.message}, className=${e.javaClass.name}" }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse(ErrorType.UNKNOWN.message, ErrorType.UNKNOWN))
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(e: NoResourceFoundException): ResponseEntity<ErrorResponse> {
        log.error("NoResourceFound Exception occurred. message={}, className={}", e.message, e.javaClass.getName())
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(ErrorType.NO_RESOURCE.message, ErrorType.NO_RESOURCE))
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(e: MissingServletRequestParameterException): ResponseEntity<ErrorResponse> {
        log.error(e) { "MissingServletRequestParameter Exception occurred. parameterName=${e.parameterName} message=${e.message}" }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(ErrorType.INVALID_PARAMETER.message, ErrorType.INVALID_PARAMETER)
            )
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        log.error("MethodArgumentTypeMismatch Exception occurred. message={}", e.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(ErrorType.INVALID_PARAMETER.message, ErrorType.INVALID_PARAMETER))
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(e: BindException): ResponseEntity<ErrorResponse> {
        log.error("Bind Exception occurred. message={}, className={}", e.message, e.javaClass.name)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(createMessage(e)?: e.message, ErrorType.INVALID_PARAMETER))
    }

    private fun createMessage(e: BindException): String? {
        if (e.fieldError != null && e.fieldError!!.defaultMessage != null) {
            return e.fieldError!!.defaultMessage
        }

        return e.fieldErrors.stream()
            .map<String> { obj: FieldError -> obj.field }
            .collect(java.util.stream.Collectors.joining(", ")) + " 값들이 정확하지 않습니다."
    }
}
