package com.search.adapter.infra.jpa.entity

import com.search.adapter.infra.jpa.enums.SearchType
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_daily_stat", indexes =
    [
        Index(name = "ix_tb_daily_stat_01", columnList = "search_type, query, created_dtm"),
    ]
)
class TbDailyStat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false)
    var id: Long? = null,

    @Column(name = "search_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val type: SearchType,

    @Column(name = "query" , nullable = false)
    val query: String,

    @Column(name = "created_dtm" , nullable = false)
    val createdDtm: LocalDateTime
)