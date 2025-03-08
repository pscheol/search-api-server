package com.search.application.stat.usecase.service

import com.search.application.stat.output.CommandDailyStatPort
import com.search.domain.entity.DailyStat
import com.search.logger.DefaultLogger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class DailyStatCommandService(
    private val commandDailyStatPort: CommandDailyStatPort
) : DefaultLogger {

    @Transactional
    fun saveDailyStat(type: String, query: String, createdDtm: LocalDateTime) {
        val dailyStat = DailyStat(type, query, createdDtm)
        log.info("## [DailyStatCommandService] ::saveDailyStat: {}", dailyStat)

        commandDailyStatPort.saveDailyStat(dailyStat)
    }
}