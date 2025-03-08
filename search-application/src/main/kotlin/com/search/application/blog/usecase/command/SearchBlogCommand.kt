package com.search.application.blog.usecase.command

data class SearchBlogCommand(
    val query: String,
    val page: Int,
    val size: Int,
)