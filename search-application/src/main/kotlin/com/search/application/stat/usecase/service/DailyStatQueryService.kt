package com.search.application.stat.usecase.service

import com.search.application.stat.output.QueryDailyStatPort
import com.search.domain.entity.QueryCount
import com.search.logger.DefaultLogger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class DailyStatQueryService(
    private val queryDailyStatPort: QueryDailyStatPort
) : DefaultLogger {

    @Transactional(readOnly = true)
    fun findQueryCount(type: String, query: String, date: LocalDate): QueryCount {
        return queryDailyStatPort.findQueryCount(type, query, date)
    }

    @Transactional(readOnly = true)
    fun findTop5Query(type: String): List<QueryCount> {
        return queryDailyStatPort.findTop5Query(type)
    }
}