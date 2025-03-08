package com.search.application.book.output

import com.search.domain.entity.Book
import com.search.dto.PageResult


interface SearchBookByKakaoPort {
    fun searchBook(query: String, page: Int, size: Int) : PageResult<Book>
}