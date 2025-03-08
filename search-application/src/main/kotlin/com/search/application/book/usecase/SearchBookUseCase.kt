package com.search.application.book.usecase

import com.search.application.book.usecase.command.SearchBookCommand
import com.search.application.book.vo.BookData
import com.search.dto.PageResult

interface SearchBookUseCase {
    fun search(command: SearchBookCommand): PageResult<BookData>
}