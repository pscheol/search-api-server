package com.search.adapter.infra.kakao.feign.config

import com.search.adapter.infra.kakao.feign.KakaoClient
import com.search.adapter.infra.kakao.feign.dto.KakaoBlogResponse
import com.search.adapter.infra.kakao.feign.dto.KakaoBookResponse
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Ignore

@Ignore
@SpringBootTest(classes = [KakaoClientTest.TestConfig::class])
@ActiveProfiles("test")
class KakaoClientTest : BehaviorSpec() {
    @EnableAutoConfiguration
    @EnableFeignClients(clients = [KakaoClient::class])
    class TestConfig

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var kakaoClient: KakaoClient

    init {
        Given("KAKAO Blog API 호출 테스트") {
            When("여행 검색어 Blog API 호출 수행") {
                val kakaoBlogResponse: KakaoBlogResponse = kakaoClient.searchBlog("여행", 1 , 10)

                Then("정상적으로 호출") {
                    println(kakaoBlogResponse)
                    kakaoBlogResponse.shouldNotBeNull()
//                    kakaoBlogResponse.meta.totalCount shouldBe 61984448
                }
            }

        }

        Given("KAKAO Book API 호출 테스트") {
            When("Kotlin 검색어 Blog API 호출 수행") {
                val kakaoBookResponse: KakaoBookResponse = kakaoClient.searchBook("Kotlin", 1 , 10)

                Then("정상적으로 호출") {
                    println(kakaoBookResponse)
                    kakaoBookResponse.shouldNotBeNull()
//                    kakaoBookResponse.meta.totalCount shouldBe 193
                }
            }

        }
    }
}

