package com.search.application.event

import com.search.application.stat.usecase.PutDailyStatUseCase
import com.search.application.stat.usecase.command.SaveDailyStatCommand
import com.search.logger.DefaultLogger
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class SearchEventHandler(
    private val putDailyStatUseCase: PutDailyStatUseCase
): DefaultLogger {
    @Async
    @EventListener
    @Throws(InterruptedException::class)
    fun searchEventHandler(event: SearchEvent) {
        log.info("## [SearchEventHandler] ::searchEventHandler: {}", event)

        val command = SaveDailyStatCommand(event.type.name, event.query, event.searchDateTime)
        putDailyStatUseCase.saveDailyStat(command)
    }
}