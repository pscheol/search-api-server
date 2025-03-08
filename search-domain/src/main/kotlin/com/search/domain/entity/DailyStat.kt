package com.search.domain.entity

import com.search.domain.enums.SearchType
import com.search.domain.vo.CreatedDtm
import com.search.domain.vo.Query
import java.time.LocalDateTime

class DailyStat(
    val type: SearchType,
    val query: Query,
    val createdDtm: CreatedDtm
) {

    constructor(
        type: String,
        query: String,
        createdDtm: LocalDateTime,
    ) : this(
        type = SearchType.valueOf(type),
        query = Query(query),
        createdDtm = CreatedDtm(createdDtm)
    )
}