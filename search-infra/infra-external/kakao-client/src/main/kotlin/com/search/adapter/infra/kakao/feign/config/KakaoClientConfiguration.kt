package com.search.adapter.infra.kakao.feign.config

import com.fasterxml.jackson.databind.ObjectMapper
import feign.RequestInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders


@Configuration
class KakaoClientConfiguration {

    @Bean
    fun requestInterceptor(@Value("\${external.kakao.headers.rest-api-key}") restApiKey: String): RequestInterceptor {
        return RequestInterceptor {
            it.header(HttpHeaders.AUTHORIZATION, "KakaoAK $restApiKey")
        }
    }

    @Bean
    fun kakaoErrorDecoder(objectMapper: ObjectMapper): KakaoErrorDecoder {
        return KakaoErrorDecoder(objectMapper)
    }
}