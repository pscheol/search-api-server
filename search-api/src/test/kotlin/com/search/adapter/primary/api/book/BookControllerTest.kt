package com.search.adapter.primary.api.book

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.search.adapter.primary.request.SearchRequest
import com.search.application.book.usecase.SearchBookUseCase
import com.search.application.book.usecase.command.SearchBookCommand
import com.search.application.book.vo.BookData
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
class BookControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    @MockkBean
    private val searchBookUseCase: SearchBookUseCase = mockk<SearchBookUseCase>(),
    ) : BehaviorSpec({

    afterContainer { clearAllMocks() }

    context("/api/v1/books 요청 성공 테스트") {
        given("Kotlin 책 검색 조회") {
            val request = SearchRequest("Kotlin", 1, 10)
            val command = SearchBookCommand(request.query, request.page, request.size)

            val extractList = listOf(BookData(
                title = "Kotlin 완전정복",
                author = "홍길동",
                publisher = "출판사",
                datetime = LocalDate.now(),
                isbn = "111",
            ))
            every { searchBookUseCase.search(command) } returns PageResult(1, 10, 1, extractList)

            When("Kotlin 책을 검색하면") {
                val result = mockMvc.perform(
                    MockMvcRequestBuilders.get("/api/v1/books")
                        .param("query", request.query)
                        .param("page", request.page.toString())
                        .param("size", request.size.toString()))

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