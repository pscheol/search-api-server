package com.search.application.stat.usecase.service

import com.search.application.stat.output.CommandDailyStatPort
import com.search.domain.entity.DailyStat
import com.search.domain.enums.SearchType
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.context.annotation.Import
import java.time.LocalDateTime


@Import(DailyStatCommandService::class)
class DailyStatCommandServiceTest : BehaviorSpec({
    val commandDailyStatPort = mockk<CommandDailyStatPort>()
    val dailyStatCommandService = DailyStatCommandService(commandDailyStatPort)

    Given("Search 조회 후 저장 시 통계 데이터 저장") {
        val bookDailyStat = DailyStat(SearchType.BOOK.name, "Kotlin", LocalDateTime.now())
        val blogDailyStat = DailyStat(SearchType.BLOG.name, "Kotlin", LocalDateTime.now())

        every { commandDailyStatPort.saveDailyStat(bookDailyStat) } returns Unit
        every { commandDailyStatPort.saveDailyStat(blogDailyStat) } returns Unit

        When("검색 통계 데이터 저장 수행하면") {
            commandDailyStatPort.saveDailyStat(bookDailyStat)

            commandDailyStatPort.saveDailyStat(blogDailyStat)

            Then("정상적으로 저장완료") {

            }
        }

    }
})