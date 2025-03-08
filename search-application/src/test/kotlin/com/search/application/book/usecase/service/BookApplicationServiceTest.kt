package com.search.application.book.usecase.service

import com.search.application.book.usecase.command.SearchBookCommand
import com.search.application.book.vo.BookData
import com.search.application.event.SearchEvent
import com.search.domain.entity.Book
import com.search.dto.PageResult
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Import

@Import(BookApplicationService::class)
class BookApplicationServiceTest : BehaviorSpec({
    val publisher: ApplicationEventPublisher = mockk()
    val bookQueryService: BookQueryService = mockk()
    val bookApplicationService = BookApplicationService(bookQueryService, publisher)

    context("책 검색 UseCase 테스트") {
        Given("Kotlin 책 검색") {
            val command = SearchBookCommand("Kotlin", 1, 10)
            val extractDomainList = listOf(
                Book(
                    title = "Kotlin 완전정복",
                    author = "홍길동",
                    publisher = "출판사",
                    datetime = "20250308",
                    isbn = "111",
                )
            )

            val extractList: List<BookData> = extractDomainList.map { BookData(it) }

            val pageReustDomainExtract = PageResult(1, 10, 1, extractDomainList)
            val pageResultExtract = PageResult(1, 10, 1, extractList)

            every { bookApplicationService.search(command) } returns pageResultExtract
            every { bookQueryService.search(command.query, command.page, command.size) } returns pageReustDomainExtract
            every { publisher.publishEvent(ofType(SearchEvent::class)) } answers { Unit }


            When("Kotlin 책 검색을 조회하면") {
                val result: PageResult<BookData> = bookApplicationService.search(command)

                verify { publisher.publishEvent(ofType(SearchEvent::class)) }

                Then("결과 값이 일치") {
                    result.page shouldBe  1
                    result.size shouldBe 10
                    result.totalElements shouldBe 1
                    result.contents shouldBeEqual extractList

                }
            }
        }
    }
})