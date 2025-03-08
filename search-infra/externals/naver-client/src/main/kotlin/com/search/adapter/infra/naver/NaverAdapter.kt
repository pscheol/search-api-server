package com.search.adapter.infra.naver

import com.search.adapter.infra.naver.feign.NaverClient
import com.search.adapter.infra.naver.feign.dto.NaverBlogResponse
import com.search.adapter.infra.naver.feign.dto.NaverBookResponse
import com.search.application.blog.output.SearchBlogByNaverPort
import com.search.application.book.output.SearchBookByNaverPort
import com.search.domain.entity.Blog
import com.search.domain.entity.Book
import com.search.dto.PageResult
import com.search.logger.DefaultLogger
import kotlinx.coroutines.*
import org.springframework.stereotype.Component

@Component
class NaverAdapter(
    private val naverClient: NaverClient,
    private val naverMapper: NaverMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : DefaultLogger, SearchBlogByNaverPort, SearchBookByNaverPort {

    override fun searchBlog(query: String, page: Int, size: Int): PageResult<Blog> = runBlocking(Dispatchers.IO) {
        log.info { "@@ NaverAdapter: searchBlog query=$query page=$page size=$size" }
        async {
            val response: NaverBlogResponse = searchBlogAPI(query, page, size)
            val blogList = response.items.stream().map { naverMapper.toDomain(it) }.toList()
            PageResult(page, size, response.total, blogList)
        }.await()
    }

    private suspend fun searchBlogAPI(query: String, page: Int, size: Int, ): NaverBlogResponse =
        withContext(dispatcher) { naverClient.searchBlog(query, page, size) }

    override fun searchBook(query: String, page: Int, size: Int): PageResult<Book> = runBlocking(Dispatchers.IO) {
        log.info { "@@ NaverAdapter: searchBook query=$query page=$page size=$size" }
        async {
            val response: NaverBookResponse = searchBookAPI(query, page, size)
            val bookList = response.items.stream().map { naverMapper.toDomain(it) }.toList()
            PageResult(page, size, response.total, bookList)
        }.await()
    }

    private suspend fun searchBookAPI(query: String, page: Int, size: Int): NaverBookResponse =
        withContext(dispatcher) { naverClient.searchBook(query, page, size) }
}