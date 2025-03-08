package com.search.application.blog.usecase.service

import com.search.application.blog.usecase.command.SearchBlogCommand
import com.search.application.blog.vo.BlogData
import com.search.application.event.SearchEvent
import com.search.domain.entity.Blog
import com.search.dto.PageResult
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Import


@Import(BlogApplicationService::class)
class BlogApplicationServiceTest : BehaviorSpec({
    val publisher: ApplicationEventPublisher = mockk()
    val blogQueryService: BlogQueryService = mockk()
    val blogApplicationService = BlogApplicationService(blogQueryService, publisher)


    Given("블로그 검색 테스트") {
        val command = SearchBlogCommand("쇼팽", 1, 10)
        val extractDomainList = listOf(
            Blog(
                title = "쇼팽은 누군가",
                contents = "쇼팽은 폴란드의 피아니스트 입니다.",
                url = "https://test.com",
                blogName = "음악세계",
                postDate = "20250308",
            )
        )
        val extractList: List<BlogData> = extractDomainList.map { BlogData(it) }

        val pageReustDomainExtract = PageResult(1, 10, 1, extractDomainList)
        val pageResultExtract = PageResult(1, 10, 1, extractList)

        every { blogApplicationService.search(command) } returns pageResultExtract
        every { blogQueryService.search(command.query, command.page, command.size) } returns pageReustDomainExtract
        every { publisher.publishEvent(ofType(SearchEvent::class)) } answers { Unit }

        When("쇼팽 관련 블로그 검색을 조회하면") {
            val result: PageResult<BlogData> = blogApplicationService.search(command)

            verify { publisher.publishEvent(ofType(SearchEvent::class)) }
            Then("결과 값이 일치") {
                result.page shouldBe 1
                result.size shouldBe 10
                result.totalElements shouldBe 1
                result.contents shouldBeEqual extractList
            }
        }
    }
})