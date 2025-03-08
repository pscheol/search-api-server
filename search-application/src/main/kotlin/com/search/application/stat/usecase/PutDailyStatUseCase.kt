package com.search.application.stat.usecase

import com.search.application.stat.usecase.command.SaveDailyStatCommand

interface PutDailyStatUseCase {
    fun saveDailyStat(command: SaveDailyStatCommand)
}