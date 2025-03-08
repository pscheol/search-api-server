package com.search.application.stat.usecase.service

import com.search.application.stat.usecase.GetDailyStatUseCase
import com.search.application.stat.usecase.PutDailyStatUseCase
import com.search.application.stat.usecase.command.GetQueryCountCommand
import com.search.application.stat.usecase.command.GetTop5QueryCommand
import com.search.application.stat.usecase.command.SaveDailyStatCommand
import com.search.application.stat.vo.StatView
import com.search.domain.entity.QueryCount
import com.search.logger.DefaultLogger
import org.springframework.stereotype.Service

@Service
class DailyStatApplicationService(
    private val dailyStatCommandService: DailyStatCommandService,
    private val dailyStatQueryService: DailyStatQueryService
) : DefaultLogger, GetDailyStatUseCase, PutDailyStatUseCase {

    override fun saveDailyStat(command: SaveDailyStatCommand) {
        dailyStatCommandService.saveDailyStat(command.type, command.query, command.createdDtm)
    }

    override fun findQueryCount(command: GetQueryCountCommand): StatView {
        val queryCount: QueryCount = dailyStatQueryService.findQueryCount(command.type, command.query ,command.date)
        return StatView(queryCount)
    }

    override fun findTop5Query(command: GetTop5QueryCommand): List<StatView> {
        return dailyStatQueryService.findTop5Query(command.type)
            .stream()
            .map { StatView(it) }
            .toList()
    }
}