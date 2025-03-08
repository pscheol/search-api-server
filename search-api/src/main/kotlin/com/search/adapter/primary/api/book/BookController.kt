package com.search.adapter.primary.api.book

import com.search.adapter.primary.api.book.dto.SearchBook
import com.search.adapter.primary.request.SearchRequest
import com.search.adapter.primary.response.ErrorResponse
import com.search.adapter.primary.response.PageResponse
import com.search.application.book.usecase.SearchBookUseCase
import com.search.application.book.usecase.command.SearchBookCommand
import com.search.application.book.vo.BookData
import com.search.dto.PageResult
import com.search.logger.DefaultLogger
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Search", description = "Search API")
@RestController
class BookController(
    private val searchBookUseCase: SearchBookUseCase,
) : DefaultLogger {

    @Operation(summary = "Search Book API", description = "책 검색결과 제공 ", tags = ["Search"])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", content = arrayOf(Content(schema = Schema(implementation = PageResult::class)))),
            ApiResponse(responseCode = "400", content = arrayOf(Content(schema = Schema(implementation = ErrorResponse::class))))
        ]
    )
    @GetMapping("/api/v1/books")
    fun searchBook(@Valid request: SearchRequest): PageResponse<SearchBook> {
        log.info { "## searchBook : search request: $request" }

        val result: PageResult<BookData> = searchBookUseCase.search(SearchBookCommand(request.query, request.page, request.size))

        return PageResponse.toBook(result)
    }
}