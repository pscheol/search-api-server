package com.search.adapter.primary.api.stats

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.search.adapter.primary.request.StatsRequest
import com.search.adapter.primary.response.StatResponse
import com.search.application.stat.usecase.GetDailyStatUseCase
import com.search.application.stat.usecase.PutDailyStatUseCase
import com.search.application.stat.usecase.command.GetQueryCountCommand
import com.search.application.stat.usecase.command.GetTop5QueryCommand
import com.search.application.stat.vo.StatView
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
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
class DailyStatsControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    @MockkBean
    private val getDailyStatUseCase: GetDailyStatUseCase = mockk<GetDailyStatUseCase>(),
    @MockkBean
    private val putDailyStatUseCase: PutDailyStatUseCase = mockk<PutDailyStatUseCase>(),
) : BehaviorSpec({

    context("/api/v1/stats 요청 성공 테스트") {
        given("쿼리 통계결과 조회") {
            val request = StatsRequest("BOOK", "Kotlin", LocalDate.now())
            val command = GetQueryCountCommand(request.type, request.query, request.date)

            val resultExtract = StatView("BOOK", "Kotlin", 5)

            every { getDailyStatUseCase.findQueryCount(command) } returns resultExtract

            When("findQueryCount API 검색하면") {
                val result = mockMvc.perform(
                    MockMvcRequestBuilders.get("/api/v1/stats")
                        .param("type", request.type)
                        .param("query", request.query)
                        .param("date", request.date.toString()))

                Then("200 Successful 이고, 데이터가 일치") {
                    result.andExpect(status().is2xxSuccessful())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.type").value("BOOK"))
                        .andExpect(jsonPath("$.query").value("Kotlin"))
                        .andExpect(jsonPath("$.count").value(5))
                }
            }
        }
    }

    context("/api/v1/stats/ranking 상위 5개 쿼리 통계결과 성공 테스트") {
        given("상위 5개 쿼리 통계결과 조회") {
            val request = StatsRequest("BOOK", "Kotlin", LocalDate.now())
            val command = GetTop5QueryCommand(request.type)

            val resultExtract = listOf(
                StatView("BOOK", "Kotlin", 10),
                StatView("BOOK", "Java", 6),
                StatView("BOOK", "Spring", 5),
                StatView("BOOK", "Reactor", 4),
                StatView("BOOK", "Kafka", 2),
            )

            every { getDailyStatUseCase.findTop5Query(command) } returns resultExtract

            When("findTop5Query API를 검색하면") {
                val result = mockMvc.perform(
                    MockMvcRequestBuilders.get("/api/v1/stats/ranking")
                        .param("type", request.type))

                Then("200 Successful 이고, 데이터가 일치") {
                    val result = result.andExpect(status().is2xxSuccessful())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn()

                    val statResponseList = objectMapper.readValue(
                        result.response.contentAsString,
                        object : TypeReference<List<StatResponse>>() {}
                    )

                    statResponseList.size shouldBe 5
                    statResponseList.forEachIndexed { index, statResponse ->
                        statResponse.type shouldBe resultExtract[index].type
                        statResponse.query shouldBe resultExtract[index].query
                        statResponse.count shouldBe resultExtract[index].count
                    }
                }
            }
        }
    }
})