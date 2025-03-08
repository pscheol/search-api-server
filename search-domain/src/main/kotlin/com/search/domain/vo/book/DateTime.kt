package com.search.domain.vo.book

import com.search.utils.DateUtils
import java.time.LocalDate

data class DateTime(val value: String = "") {
    fun parseLocalDate(): LocalDate {
        return DateUtils.parseYYYYMMDD(value)
    }
}
