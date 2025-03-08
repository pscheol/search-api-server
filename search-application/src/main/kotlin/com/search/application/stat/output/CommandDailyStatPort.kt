package com.search.application.stat.output

import com.search.domain.entity.DailyStat

interface CommandDailyStatPort {
    fun saveDailyStat(dailyStat: DailyStat)
}