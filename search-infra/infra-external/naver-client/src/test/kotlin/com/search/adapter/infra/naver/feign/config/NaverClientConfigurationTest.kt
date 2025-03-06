package com.search.adapter.infra.naver.feign.config

import feign.RequestInterceptor
import feign.RequestTemplate
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull

class NaverClientConfigurationTest : BehaviorSpec({
    val naverClientConfiguration = NaverClientConfiguration()

    context("requestInterceptor에 header 값 적용 테스트") {
        given("RequestTemplate, api key 초기값 세팅") {
            val template = RequestTemplate()
            val clientId = "id"
            val clientSecret = "secret"

            When("requestInterceptor 타기 전 ") {
                then("template의 header값은 존재하지 않는다.") {
                    template.headers()["X-Naver-Client-Id"].shouldBeNull()
                    template.headers()["X-Naver-Client-Secret"].shouldBeNull()
                }
            }

            When("requestInterceptor api key 대입") {
                val interceptor: RequestInterceptor = naverClientConfiguration.requestInterceptor(clientId, clientSecret)
                interceptor.apply(template)

                then("template의 header 값이 추가된것을 확인") {
                    template.headers()["X-Naver-Client-Id"]?.contains(clientId)
                    template.headers()["X-Naver-Client-Secret"]?.contains(clientSecret)
                }
            }
        }
    }
})