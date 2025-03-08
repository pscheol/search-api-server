package com.search.application.stat.usecase.command

import java.time.LocalDateTime

data class SaveDailyStatCommand(
    val type: String,
    val query: String,
    val createdDtm: LocalDateTime
)
