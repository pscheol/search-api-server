package com.search.application.blog.usecase.service

import com.search.application.blog.usecase.SearchBlogUseCase
import com.search.application.blog.usecase.command.SearchBlogCommand
import com.search.application.blog.vo.BlogData
import com.search.application.event.SearchEvent
import com.search.domain.entity.Blog
import com.search.domain.enums.SearchType
import com.search.dto.PageResult
import com.search.logger.DefaultLogger
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BlogApplicationService(
    private val blogQueryService: BlogQueryService,
    private val publisher: ApplicationEventPublisher
): DefaultLogger, SearchBlogUseCase {


    override fun search(command: SearchBlogCommand): PageResult<BlogData> {
        val result: PageResult<Blog> = blogQueryService.search(command.query, command.page, command.size)

        if (result.contents.isNotEmpty()) {
            publisher.publishEvent(SearchEvent(SearchType.BLOG, command.query, LocalDateTime.now()))
        }

        val blogDataList: List<BlogData> = result.contents.stream().map { BlogData(it) }.toList()

        return PageResult(result.page, result.size ,result.totalElements , blogDataList)
    }
}