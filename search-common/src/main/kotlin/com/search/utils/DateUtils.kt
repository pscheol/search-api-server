package com.search.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
    private val yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd")

    fun parseYYYYMMDD(src : String) = LocalDate.parse(src, yyyyMMdd)

    fun parseOffsetDateTime(datetime: String) = LocalDateTime.parse(datetime, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}