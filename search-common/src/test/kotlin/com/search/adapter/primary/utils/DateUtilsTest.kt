package com.search.adapter.primary.utils

import com.search.utils.DateUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.LocalDateTime

class DateUtilsTest : BehaviorSpec({
    given("문자열(yyyyMMdd)을 LocalDate로 변환") {
        val date: String = "20250306"

        val extractLocalDate = LocalDate.of(2025, 3,6)


        When("parseYYYYMMDD 함수를 수행하면") {
            val result = DateUtils.parseYYYYMMDD(date);

            Then("LocalDate 값이 일치") {
                result shouldBe extractLocalDate
            }
        }
    }

    given("ISO_OFFSET_DATE_TIME 문자열 날짜를 LocalDateTime로 변환") {
        val datetime: String = "2025-03-06T10:30:00+09:00"

        val extractLocalDateTime = LocalDateTime.of(2025, 3,6, 10, 30)


        When("parseYYYYMMDD 함수를 수행하면") {
            val result = DateUtils.parseOffsetDateTime(datetime);

            Then("LocalDate 값이 일치") {
                result shouldBe extractLocalDateTime
            }
        }
    }
})

