package com.search.adapter.infra.jpa

import com.search.adapter.infra.jpa.entity.TbDailyStat
import com.search.adapter.infra.jpa.enums.SearchType
import com.search.domain.entity.DailyStat
import com.search.domain.entity.QueryCount
import org.springframework.stereotype.Component

@Component
class DailyStatMapper {
    fun toDomain(type: SearchType, query: String, count: Long): QueryCount {
        return QueryCount(
            type = type.name,
            query = query,
            count = count
        )
    }

    fun toEntity(src: DailyStat): TbDailyStat {
        return TbDailyStat(
            type = SearchType.valueOf(src.type.name),
            query = src.query.value,
            createdDtm = src.createdDtm.datetime
        )
    }
}