package com.search.application.stat.vo

import com.search.domain.entity.QueryCount

data class StatView(
    val type: String,
    val query: String,
    val count: Long,
) {
    constructor(
        queryCount: QueryCount
    ) : this(
        type = queryCount.type.name,
        query = queryCount.query.value,
        count = queryCount.count.value
    )
}
