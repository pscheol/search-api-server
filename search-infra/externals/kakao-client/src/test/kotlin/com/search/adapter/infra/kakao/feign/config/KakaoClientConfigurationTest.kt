package com.search.adapter.infra.kakao.feign.config

import feign.RequestInterceptor
import feign.RequestTemplate
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull
import org.springframework.http.HttpHeaders

class KakaoClientConfigurationTest : BehaviorSpec({
  val kakaoClientConfiguration = KakaoClientConfiguration()

 context("requestInterceptor에 header 값 적용 테스트") {
  given("RequestTemplate, api key 초기값 세팅") {
   val template = RequestTemplate()
   val restAPIKey: String = "key"

   When ("requestInterceptor 타기 전 ") {
    then ("template의 header값은 존재하지 않는다.") {
     template.headers()[HttpHeaders.AUTHORIZATION].shouldBeNull()
    }
   }

   When("requestInterceptor api key 대입") {
    val interceptor: RequestInterceptor = kakaoClientConfiguration.kakaoRequestInterceptor(restAPIKey)
    interceptor.apply(template)

    then("template의 header 값이 추가된것을 확인") {
     println("값 "+ template.headers()[HttpHeaders.AUTHORIZATION])
     template.headers()[HttpHeaders.AUTHORIZATION]?.contains("KakaoAK $restAPIKey")
    }
   }
  }
 }
})

