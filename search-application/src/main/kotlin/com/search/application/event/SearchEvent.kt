package com.search.application.event

import com.search.domain.enums.SearchType
import java.time.LocalDateTime

data class SearchEvent(
    val type: SearchType,
    val query: String,
    val searchDateTime: LocalDateTime
)
