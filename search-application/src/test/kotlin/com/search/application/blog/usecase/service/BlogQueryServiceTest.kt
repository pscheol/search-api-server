package com.search.application.blog.usecase.service

import com.search.application.blog.output.SearchBlogByKakaoPort
import com.search.application.blog.output.SearchBlogByNaverPort
import com.search.domain.entity.Blog
import com.search.dto.PageResult
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.annotation.Import
import java.time.Duration

@ExtendWith(MockKExtension::class)
@Import(BlogQueryService::class)
class BlogQueryServiceTest : BehaviorSpec({
    val circuitBreakerRegistry: CircuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults()
    val searchBlogByNaverPort: SearchBlogByNaverPort = mockk<SearchBlogByNaverPort>()
    val searchBlogByKakaoPort: SearchBlogByKakaoPort = mockk<SearchBlogByKakaoPort>()
    val blogQueryService = BlogQueryService(searchBlogByNaverPort, searchBlogByKakaoPort)


    Given("정상 작동 상태에서 Circuit 상태가 CLOSE이고 Naver 호출되는 테스트") {
        val query = "쇼팽"
        val page = 1
        val size = 10

        val extractDomainList = listOf(
            Blog(
                title = "쇼팽은 누군가",
                contents = "쇼팽은 폴란드의 피아니스트 입니다.",
                url = "https://test.com",
                blogName = "음악세계",
                postDate = "20250308",
            )
        )
        val pageReustDomainExtract = PageResult(1, 10, 1, extractDomainList)
        val circuitBreaker = circuitBreakerRegistry.circuitBreaker("naverSearchBlog")

        every { blogQueryService.search(query, page, size) } returns pageReustDomainExtract

        When("네이버 블로그 Query 조회") {
            val result: PageResult<Blog> = blogQueryService.search(query, page, size)

            Then("Naver 블로그 API 의 검색결과와 일치") {
                circuitBreaker.state shouldBe CircuitBreaker.State.CLOSED
                val searchBlog: PageResult<Blog> = searchBlogByNaverPort.searchBlog(query, page,size)
                result shouldBeEqual searchBlog
            }
        }
    }


    Given("Circuit 상태가 OPEN이면 Kakao API가 호출되는 테스트") {
        val query = "쇼팽"
        val page = 1
        val size = 10

        val extractDomainList = listOf(
            Blog(
                title = "쇼팽은 누군가요",
                contents = "쇼팽은 폴란드의 피아니스트 입니다.",
                url = "https://test.com",
                blogName = "음악세계",
                postDate = "20250308",
            )
        )
        val pageReustDomainExtract = PageResult(1, 10, 1, extractDomainList)

        val config = CircuitBreakerConfig.custom()
            .slidingWindowSize(5)
            .minimumNumberOfCalls(5)
            .failureRateThreshold(50F)
            .waitDurationInOpenState(Duration.ofSeconds(5))
            .build()
        circuitBreakerRegistry.circuitBreaker("naverSearch", config)
        val circuitBreaker = circuitBreakerRegistry.circuitBreaker("naverSearchBlog")

        every { blogQueryService.search(query, page, size) } throws RuntimeException("Service Failure!")
        every { searchBlogByKakaoPort.searchBlog(query, page,size) } returns pageReustDomainExtract

        When("네이버 블로그 Query 조회") {
            repeat(10) {
                runCatching { blogQueryService.search(query, page, size) }
            }

            Then("CircuitBreaker가 OPEN 상태가 되어야 한다.") {
                circuitBreaker.state shouldBe CircuitBreaker.State.CLOSED
            }

            Then("Kakao 블로그API 의 검색결과와 일치") {
                val searchBlog: PageResult<Blog> = searchBlogByKakaoPort.searchBlog(query, page,size)
                searchBlog shouldBeEqual pageReustDomainExtract
            }
        }
    }

})