package com.search.adapter.infra.naver.feign.config

import com.search.adapter.infra.naver.feign.NaverClient
import com.search.adapter.infra.naver.feign.dto.NaverBlogResponse
import com.search.adapter.infra.naver.feign.dto.NaverBookResponse
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
@SpringBootTest(classes = [NaverClientTest.TestConfig::class])
@ActiveProfiles("test")
class NaverClientTest : BehaviorSpec() {
    @EnableAutoConfiguration
    @EnableFeignClients(clients = [NaverClient::class])
    class TestConfig

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var naverClient: NaverClient

    init {
        Given("Naver Blog API 호출 테스트") {
            When("여행 검색어 Blog API 호출 수행") {
                val naverBlogResponse: NaverBlogResponse = naverClient.searchBlog("여행", 1 , 10)

                Then("정상적으로 호출") {
                    println(naverBlogResponse)
                    naverBlogResponse.shouldNotBeNull()
//                    naverBlogResponse.total shouldBe 67053068
                }
            }

        }

        Given("Naver Book API 호출 테스트") {
            When("Kotlin 검색어 Blog API 호출 수행") {
                val naverBookResponse: NaverBookResponse = naverClient.searchBook("Kotlin", 1 , 10)

                Then("정상적으로 호출") {
                    println(naverBookResponse)
                    naverBookResponse.shouldNotBeNull()
//                    naverBookResponse.total shouldBe 70
                }
            }

        }
    }
}

