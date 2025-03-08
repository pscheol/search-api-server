package com.search.adapter.primary.api.blog

import com.search.adapter.primary.api.blog.dto.SearchBlog
import com.search.adapter.primary.request.SearchRequest
import com.search.adapter.primary.response.ErrorResponse
import com.search.adapter.primary.response.PageResponse
import com.search.application.blog.usecase.SearchBlogUseCase
import com.search.application.blog.usecase.command.SearchBlogCommand
import com.search.application.blog.vo.BlogData
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
class BlogController(
    private val searchBlogUseCase: SearchBlogUseCase,
): DefaultLogger {

    @Operation(summary = "Search Blog API", description = "블로그 검색결과 제공 ", tags = ["Search"])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", content = arrayOf(Content(schema = Schema(implementation = PageResult::class)))),
            ApiResponse(responseCode = "400", content = arrayOf(Content(schema = Schema(implementation = ErrorResponse::class))))
        ]
    )
    @GetMapping("/api/v1/blogs")
    fun searchBlog(@Valid request: SearchRequest): PageResponse<SearchBlog> {
        log.info { "## searchBlog : search request: $request" }

        val result: PageResult<BlogData> = searchBlogUseCase.search(SearchBlogCommand(request.query, request.page, request.size))

        return PageResponse.toBlog(result)
    }
}