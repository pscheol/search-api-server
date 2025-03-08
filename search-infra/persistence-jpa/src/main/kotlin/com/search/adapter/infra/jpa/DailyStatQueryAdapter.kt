package com.search.adapter.infra.jpa

import com.search.adapter.infra.jpa.dto.QueryCountDTO
import com.search.adapter.infra.jpa.enums.SearchType
import com.search.adapter.infra.jpa.repository.TbDailyStatRepository
import com.search.application.stat.output.QueryDailyStatPort
import com.search.domain.entity.QueryCount
import com.search.logger.DefaultLogger
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime

@Component
class DailyStatQueryAdapter(
    private val tbDailyStatRepository: TbDailyStatRepository,
    private val dailyStatMapper: DailyStatMapper
) : DefaultLogger, QueryDailyStatPort {
    companion object {
        private val PAGE: Int = 0
        private val SIZE: Int = 5
    }

    override fun findQueryCount(type: String, query: String, date: LocalDate): QueryCount {
        val searchType = SearchType.valueOf(type)

        val count: Long = tbDailyStatRepository.countByTypeAndQueryAndCreatedDtmBetween(
            searchType,
            query,
            date.atStartOfDay(),
            date.atTime(LocalTime.MAX)
        )

        return dailyStatMapper.toDomain(searchType, query, count)
    }

    override fun findTop5Query(type: String): List<QueryCount> {
        val counts: List<QueryCountDTO> = tbDailyStatRepository.findTopQuery(
            SearchType.valueOf(type),
            PageRequest.of(PAGE, SIZE)
        )

        return counts.stream()
            .map { dailyStatMapper.toDomain(it.type, it.query, it.count) }
            .toList()
    }

}