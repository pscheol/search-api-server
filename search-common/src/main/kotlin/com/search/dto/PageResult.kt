package com.search.dto

data class PageResult<T>(
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val contents: List<T>
)