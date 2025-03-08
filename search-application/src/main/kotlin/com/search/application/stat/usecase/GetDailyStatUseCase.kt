package com.search.application.stat.usecase

import com.search.application.stat.usecase.command.GetQueryCountCommand
import com.search.application.stat.usecase.command.GetTop5QueryCommand
import com.search.application.stat.vo.StatView

interface GetDailyStatUseCase {
    fun findQueryCount(command : GetQueryCountCommand): StatView
    fun findTop5Query(command: GetTop5QueryCommand): List<StatView>
}