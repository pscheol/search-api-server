package com.search.application.blog.output

import com.search.domain.entity.Blog
import com.search.dto.PageResult

interface SearchBlogByNaverPort {
    fun searchBlog(query: String, page: Int, size: Int): PageResult<Blog>
}