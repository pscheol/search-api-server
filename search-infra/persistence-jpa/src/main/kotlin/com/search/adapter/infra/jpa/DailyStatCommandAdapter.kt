package com.search.adapter.infra.jpa

import com.search.adapter.infra.jpa.repository.TbDailyStatRepository
import com.search.application.stat.output.CommandDailyStatPort
import com.search.domain.entity.DailyStat
import org.springframework.stereotype.Component

@Component
class DailyStatCommandAdapter(
    private val tbDailyStatRepository: TbDailyStatRepository,
    private val dailyStatMapper: DailyStatMapper
) : CommandDailyStatPort {

    override fun saveDailyStat(dailyStat: DailyStat) {
        tbDailyStatRepository.save(dailyStatMapper.toEntity(dailyStat))
    }
}