package com.search.application.book.vo

import com.search.domain.entity.Book
import java.time.LocalDate

data class BookData(
    val title: String,
    val author: String,
    val publisher: String,
    val datetime: LocalDate,
    val isbn: String
) {
    constructor(
        book: Book
    ) : this(
        title = book.title.value,
        author = book.author.value,
        publisher = book.publisher.name,
        datetime = book.datetime.parseLocalDate(),
        isbn = book.isbn.value,
    )
}
