package com.search.adapter.infra.naver.feign.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.search.adapter.infra.naver.feign.dto.NaverErrorResponse
import com.search.exception.ErrorType
import com.search.exception.SearchAPIException
import com.search.logger.DefaultLogger
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus
import java.io.IOException
import java.nio.charset.StandardCharsets

class NaverErrorDecoder(
    private val objectMapper: ObjectMapper,
): ErrorDecoder, DefaultLogger {

    override fun decode(methodKey: String, response: Response): Exception {
        try {
            val body = String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8)
            val errorResponse: NaverErrorResponse = objectMapper.readValue(body, NaverErrorResponse::class.java)
            throw SearchAPIException(
                errorResponse.errorMessage,
                ErrorType.EXTERNAL_API_ERROR,
                HttpStatus.valueOf(response.status())
            )
        } catch (e: IOException) {
            log.error(e) { "Naver Error message parsing failed. [code=${response.status()}, request=${response.request()}, methodKey=$methodKey]" }
            throw SearchAPIException(
                "Naver Error message parsing failed.",
                ErrorType.EXTERNAL_API_ERROR,
                HttpStatus.valueOf(response.status())
            )
        }
    }
}