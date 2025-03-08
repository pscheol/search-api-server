package com.search.domain.entity

import com.search.domain.enums.SearchType
import com.search.domain.vo.Count
import com.search.domain.vo.Query

class QueryCount(
    val type: SearchType,
    val query: Query,
    val count: Count
) {
    constructor(
        type: String,
        query: String,
        count: Long,
    ) : this(
        type = SearchType.valueOf(type),
        query = Query(query),
        count = Count(count)
    )


    override fun toString(): String {
        return "QueryCount(type=$type, query=$query, count=$count)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is QueryCount) return false

        if (type != other.type) return false
        if (query != other.query) return false
        if (count != other.count) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + query.hashCode()
        result = 31 * result + count.hashCode()
        return result
    }


}
