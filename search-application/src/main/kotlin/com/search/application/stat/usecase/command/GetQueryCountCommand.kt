package com.search.application.stat.usecase.command

import java.time.LocalDate

data class GetQueryCountCommand(
    val type: String,
    val query: String,
    val date: LocalDate
)
