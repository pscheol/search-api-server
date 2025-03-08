package com.search.adapter.infra.jpa.repository

import com.search.adapter.infra.jpa.dto.QueryCountDTO
import com.search.adapter.infra.jpa.entity.TbDailyStat
import com.search.adapter.infra.jpa.enums.SearchType
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface TbDailyStatRepository : JpaRepository<TbDailyStat, Long> {

    fun countByTypeAndQueryAndCreatedDtmBetween(
        type: SearchType,
        query: String,
        startDtm: LocalDateTime,
        endDtm: LocalDateTime
    ) : Long

    @Query("""
        SELECT new com.search.adapter.infra.jpa.dto.QueryCountDTO(ds.type, ds.query, count(ds.query))
          FROM TbDailyStat ds
          WHERE ds.type = :type
          GROUP BY ds.type, ds.query
          ORDER BY count(ds.query) DESC
    """)
    fun findTopQuery(type: SearchType, pageable: Pageable): List<QueryCountDTO>

}