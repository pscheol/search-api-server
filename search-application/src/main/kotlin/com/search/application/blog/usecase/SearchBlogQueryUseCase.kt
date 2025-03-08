package com.search.application.blog.usecase

import com.search.application.blog.usecase.command.SearchBlogCommand
import com.search.application.blog.vo.BlogData
import com.search.dto.PageResult

interface SearchBlogQueryUseCase {
    fun search(command: SearchBlogCommand): PageResult<BlogData>
}