package com.search.adapter.primary.api.book.dto

import com.search.application.book.vo.BookData
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "책 검색결과")
data class SearchBook(
    @Schema(description = "제목", example = "Kotlin In Action")
    val title: String,

    @Schema(description = "저자", example = "드미트리 제메로프")
    val author: String,

    @Schema(defaultValue = "출판사", example = "에이콘 출판사")
    val publisher: String,

    @Schema(defaultValue = "출판일", example = "2017-10-31")
    val datetime: LocalDate,

    @Schema(defaultValue = "isbn", example = "9791161750712")
    val isbn: String,
) {

    constructor(src : BookData) : this
    (
        title = src.title,
        author = src.author,
        publisher = src.publisher,
        datetime = src.datetime,
        isbn = src.isbn,
    )


    companion object {
        fun of(blogData: List<BookData>): List<SearchBook> {
            return blogData.stream().map { SearchBook(it) }.toList()
        }
    }
}