package com.search.application.book.usecase.command

data class SearchBookCommand(
    val query: String,
    val page: Int,
    val size: Int,
)
