package com.search.domain.vo.blog

import com.search.utils.DateUtils
import java.time.LocalDate

data class PostDate(val postDate: String) {
    fun parseLocalDate(): LocalDate {
        return DateUtils.parseYYYYMMDD(postDate)
    }
}
