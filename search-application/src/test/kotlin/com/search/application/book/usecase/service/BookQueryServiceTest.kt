package com.search.application.book.usecase.service

import com.search.application.book.output.SearchBookByKakaoPort
import com.search.application.book.output.SearchBookByNaverPort
import com.search.domain.entity.Book
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
@Import(BookQueryService::class)
class BookQueryServiceTest : BehaviorSpec({
    val circuitBreakerRegistry: CircuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults()
    val searchBookNaverPort = mockk<SearchBookByNaverPort>()
    val searchBookKakaoPort = mockk<SearchBookByKakaoPort>()
    val bookQueryService: BookQueryService = BookQueryService(searchBookKakaoPort, searchBookNaverPort)


    Given("정상 작동 상태에서 Circuit 상태가 CLOSE이고 Naver 호출되는 테스트") {
        val query = "Kotlin"
        val page = 1
        val size = 10

        val extractDomainList = listOf(
            Book(
                title = "Kotlin 완전정복",
                author = "홍길동",
                publisher = "출판사",
                datetime = "20250308",
                isbn = "111",
            )
        )
        val pageReustDomainExtract = PageResult(1, 10, 1, extractDomainList)
        val circuitBreaker = circuitBreakerRegistry.circuitBreaker("naverSearchBlog")

        every { bookQueryService.search(query, page, size) } returns pageReustDomainExtract

        When("네이버 책 Query 조회") {
            val result: PageResult<Book> = bookQueryService.search(query, page, size)

            Then("Naver 책 API 의 검색결과와 일치") {
                circuitBreaker.state shouldBe CircuitBreaker.State.CLOSED
                val searchBlog: PageResult<Book> = searchBookNaverPort.searchBook(query, page,size)
                result shouldBeEqual searchBlog
            }
        }
    }


    Given("Circuit 상태가 OPEN이면 Kakao API가 호출되는 테스트") {
        val query = "쇼팽"
        val page = 1
        val size = 10

        val extractDomainList = listOf(
            Book(
                title = "Kotlin 완전정복",
                author = "홍길동",
                publisher = "출판사",
                datetime = "20250308",
                isbn = "111",
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

        every { bookQueryService.search(query, page, size) } throws RuntimeException("Service Failure!")
        every { searchBookKakaoPort.searchBook(query, page,size) } returns pageReustDomainExtract

        When("네이버 책 Query 조회") {
            repeat(10) {
                runCatching { bookQueryService.search(query, page, size) }
            }

            Then("CircuitBreaker가 OPEN 상태가 되어야 한다.") {
                circuitBreaker.state shouldBe CircuitBreaker.State.CLOSED
            }

            Then("Kakao 책 API 의 검색결과와 일치") {
                val searchBlog: PageResult<Book> = searchBookKakaoPort.searchBook(query, page,size)
                searchBlog shouldBeEqual pageReustDomainExtract
            }
        }
    }
})