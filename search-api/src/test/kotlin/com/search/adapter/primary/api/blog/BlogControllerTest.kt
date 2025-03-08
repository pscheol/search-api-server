package com.search.adapter.primary.api.blog

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.search.adapter.primary.request.SearchRequest
import com.search.application.blog.usecase.SearchBlogUseCase
import com.search.application.blog.usecase.command.SearchBlogCommand
import com.search.application.blog.vo.BlogData
import com.search.dto.PageResult
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class BlogControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    @MockkBean
    private val searchBlogUseCase: SearchBlogUseCase = mockk<SearchBlogUseCase>(),
) : BehaviorSpec({

    afterContainer { clearAllMocks() }

    context("/api/v1/blogs 요청 성공 테스트") {
        given("요리 관련 블로그 검색 조회") {
            val request = SearchRequest("요리", 1, 10)
            val command = SearchBlogCommand(request.query, request.page, request.size)

            val extractList = listOf(
                BlogData(
                    title = "요리당",
                    contents = "요리는 정성 입니다.",
                    url = "https://test.com",
                    blogName = "요리왕",
                    postDate = LocalDate.now(),
                )
            )
            every { searchBlogUseCase.search(command) } returns PageResult(1, 10, 1, extractList)

            When("Kotlin 책을 검색하면") {
                val result = mockMvc.perform(
                    MockMvcRequestBuilders.get("/api/v1/blogs")
                        .param("query", request.query)
                        .param("page", request.page.toString())
                        .param("size", request.size.toString())
                )

                Then("200 Successful 이고, 데이터가 일치") {
                    result.andExpect(status().is2xxSuccessful())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.page").value(1))
                        .andExpect(jsonPath("$.size").value(10))
                        .andExpect(jsonPath("$.totalElements").value(1))
                        .andExpect(jsonPath("$.contents").isArray())
                }
            }
        }
    }
})