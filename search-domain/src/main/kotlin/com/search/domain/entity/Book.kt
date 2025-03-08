package com.search.domain.entity

import com.search.domain.vo.book.*
import java.io.Serializable

class Book(
    val title: BookTitle,
    val author: Author,
    val publisher: Publisher,
    val datetime: DateTime,
    val isbn: ISBN
) : Serializable {

    constructor(
        title: String,
        author: String,
        publisher: String,
        datetime: String,
        isbn: String,
    ) : this(
        title = BookTitle(title),
        author = Author(author),
        publisher = Publisher(publisher),
        datetime = DateTime(datetime),
        isbn = ISBN(isbn),
    )
}