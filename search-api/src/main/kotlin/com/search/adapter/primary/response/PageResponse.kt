package com.search.adapter.primary.response

import com.search.adapter.primary.api.blog.dto.SearchBlog
import com.search.adapter.primary.api.book.dto.SearchBook
import com.search.application.blog.vo.BlogData
import com.search.application.book.vo.BookData
import com.search.dto.PageResult


data class PageResponse<T>(
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val contents: List<T>
) {
    companion object {
        fun toBlog(result: PageResult<BlogData>): PageResponse<SearchBlog> {
            return PageResponse(
                result.page,
                result.size,
                result.totalElements,
                SearchBlog.of(result.contents)
            )
        }

        fun toBook(result: PageResult<BookData>): PageResponse<SearchBook> {
            return PageResponse(
                result.page,
                result.size,
                result.totalElements,
                SearchBook.of(result.contents)
            )
        }
    }
}