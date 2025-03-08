package com.search.application.book.usecase.service

import com.search.application.book.usecase.SearchBookUseCase
import com.search.application.book.usecase.command.SearchBookCommand
import com.search.application.book.vo.BookData
import com.search.application.event.SearchEvent
import com.search.domain.entity.Book
import com.search.domain.enums.SearchType
import com.search.dto.PageResult
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BookApplicationService(
    private val bookQueryService: BookQueryService,
    private val publisher: ApplicationEventPublisher,
): SearchBookUseCase {

    override fun search(command: SearchBookCommand): PageResult<BookData> {
        val result: PageResult<Book> = bookQueryService.search(command.query, command.page, command.size)

        if (result.contents.isNotEmpty()) {
            publisher.publishEvent(SearchEvent(SearchType.BOOK, command.query, LocalDateTime.now()))
        }

        val bookDataList: List<BookData> = result.contents.stream().map { BookData(it) }.toList()

        return PageResult(result.page, result.size ,result.totalElements , bookDataList)
    }
}