package com.search.adapter.primary.api.stats

import com.search.adapter.primary.request.StatsRequest
import com.search.adapter.primary.response.ErrorResponse
import com.search.adapter.primary.response.StatResponse
import com.search.application.stat.usecase.GetDailyStatUseCase
import com.search.application.stat.usecase.command.GetQueryCountCommand
import com.search.application.stat.usecase.command.GetTop5QueryCommand
import com.search.logger.DefaultLogger
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Stats", description = "Stats API")
@RestController
class DailyStatsController(
    private val getDailyStatUseCase: GetDailyStatUseCase,
) : DefaultLogger {

    @Operation(summary = "Stats API", description = "쿼리 통계결과", tags = ["Stats"])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", content = arrayOf(Content(schema = Schema(implementation = StatResponse::class)))),
            ApiResponse(responseCode = "400", content = arrayOf(Content(schema = Schema(implementation = ErrorResponse::class))))
        ]
    )
    @GetMapping("/api/v1/stats")
    fun findQueryStat(@Valid request: StatsRequest) : StatResponse {
        log.info { "## DailyStatController : findQueryStat request=$request" }
        val result = getDailyStatUseCase.findQueryCount(GetQueryCountCommand(request.type, request.query, request.date))

        return StatResponse(result.type, result.query, result.count)
    }

    @Operation(summary = "Stats ranking API", description = "상위 5개 쿼리 통계결과", tags = ["Stats"])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", content = arrayOf(Content(schema = Schema(implementation = StatResponse::class)))),
            ApiResponse(responseCode = "400", content = arrayOf(Content(schema = Schema(implementation = ErrorResponse::class))))
        ]
    )
    @GetMapping("/api/v1/stats/ranking")
    fun findTop5QueryStat(
        @Valid
        @NotNull(message = "검색 타입이 누락되었습니다.]")
        @Pattern(regexp = "^(BOOK|BLOG)$", message = "잘못된 검색타입 입니다.[BOOK, BLOG]")
        @RequestParam("type") type: String,
    ) : List<StatResponse> {
        log.info { "## DailyStatController : findTop5QueryStat type=$type" }

        return getDailyStatUseCase.findTop5Query(GetTop5QueryCommand(type))
            .stream().map { StatResponse(it.type, it.query, it.count) }
            .toList()
    }
}