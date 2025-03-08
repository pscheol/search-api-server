package com.search.application.stat.output

import com.search.domain.entity.QueryCount
import java.time.LocalDate

interface QueryDailyStatPort {
    fun findQueryCount(type: String, query: String, date: LocalDate): QueryCount
    fun findTop5Query(type: String): List<QueryCount>
}