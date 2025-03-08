package com.search.adapter.infra.naver.feign.config

import com.fasterxml.jackson.databind.ObjectMapper
import feign.RequestInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class NaverClientConfiguration {

    @Bean
    fun naverRequestInterceptor(@Value("\${external.naver.headers.client-id}") clientId: String,
                                @Value("\${external.naver.headers.client-secret}") clientSecret: String
    ): RequestInterceptor {
        return RequestInterceptor {
            it.header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
        }
    }

    @Bean
    fun naverErrorDecoder(objectMapper: ObjectMapper): NaverErrorDecoder {
        return NaverErrorDecoder(objectMapper)
    }
}