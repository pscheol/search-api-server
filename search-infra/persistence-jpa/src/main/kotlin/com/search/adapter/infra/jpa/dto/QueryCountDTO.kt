package com.search.adapter.infra.jpa.dto

import com.search.adapter.infra.jpa.enums.SearchType

data class QueryCountDTO(
    val type: SearchType,
    val query: String,
    val count: Long
)