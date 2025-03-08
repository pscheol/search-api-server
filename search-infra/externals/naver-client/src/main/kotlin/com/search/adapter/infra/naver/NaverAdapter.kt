package com.search.adapter.infra.naver

import com.search.adapter.infra.naver.feign.NaverClient
import com.search.adapter.infra.naver.feign.dto.NaverBlogResponse
import com.search.adapter.infra.naver.feign.dto.NaverBookResponse
import com.search.application.blog.output.SearchBlogByNaverPort
import com.search.application.book.output.SearchBookByNaverPort
import com.search.domain.entity.Blog
import com.search.domain.entity.Book
import com.search.dto.PageResult
import org.springframework.stereotype.Component

@Component
class NaverAdapter(
    private val naverClient: NaverClient,
    private val naverMapper: NaverMapper
) : SearchBlogByNaverPort, SearchBookByNaverPort {

    override fun searchBlog(query: String, page: Int, size: Int): PageResult<Blog> {
        val response: NaverBlogResponse = naverClient.searchBlog(query, page, size)
        val blogList = response.items.stream().map { naverMapper.toDomain(it) }.toList()
        return PageResult(page, size, response.total, blogList);
    }

    override fun searchBook(query: String, page: Int, size: Int): PageResult<Book> {
        val response: NaverBookResponse = naverClient.searchBook(query, page, size)
        val bookList = response.items.stream().map { naverMapper.toDomain(it) }.toList()
        return PageResult(page, size, response.total, bookList);
    }
}