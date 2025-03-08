package com.search.application.stat.usecase.service

import com.search.application.stat.output.QueryDailyStatPort
import com.search.domain.entity.QueryCount
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.context.annotation.Import
import java.time.LocalDate

@Import(DailyStatQueryService::class)
class DailyStatQueryServiceTest : BehaviorSpec({
    val queryDailyStatPort = mockk<QueryDailyStatPort>()
    val dailyStatQueryService = DailyStatQueryService(queryDailyStatPort)


    given("findQueryCount 조회 시 하루치 쿼리 데이터 반환") {
        val type = "BLOG"
        val query = "쇼팽"
        val date = LocalDate.now()

        val queryCountExtract = QueryCount(type, query, 10)
        every { dailyStatQueryService.findQueryCount(type, query, date) } returns queryCountExtract

        When("findQueryCount를 수행하면") {
            val result = dailyStatQueryService.findQueryCount(type, query, date)

            Then("결과 값이 일치한다.") {
                val queryCount = queryDailyStatPort.findQueryCount(type, query, date)
                result shouldBe queryCount
            }
        }
    }


    given("findTop5Query 조회 시 상위 5개 데이터 반환") {
        val type = "BLOG"

        val queryCountExtract = listOf(
            QueryCount(type, "쇼팽", 10),
            QueryCount(type, "사진", 5),
            QueryCount(type, "여행", 2),
            QueryCount(type, "기술", 1),
            QueryCount(type, "게임", 1),
        )
        every { dailyStatQueryService.findTop5Query(type) } returns queryCountExtract

        When("findTop5Query 수행하면") {
            val result = dailyStatQueryService.findTop5Query(type)

            Then("결과 값이 일치한다.") {
                val queryCount = queryDailyStatPort.findTop5Query(type)
                result shouldBe queryCount
            }
        }
    }
})