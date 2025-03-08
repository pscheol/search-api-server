package com.search.adapter.infra.kakao

import com.search.adapter.infra.kakao.feign.KakaoClient
import com.search.adapter.infra.kakao.feign.dto.KakaoBlogResponse
import com.search.adapter.infra.kakao.feign.dto.KakaoBookResponse
import com.search.application.blog.output.SearchBlogByKakaoPort
import com.search.application.book.output.SearchBookByKakaoPort
import com.search.domain.entity.Blog
import com.search.domain.entity.Book
import com.search.dto.PageResult
import com.search.logger.DefaultLogger
import kotlinx.coroutines.*
import org.springframework.stereotype.Service

@Service
class KakaoAdapter(
    private val kakaoClient: KakaoClient,
    private val kakaoMapper: KakaoMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : DefaultLogger, SearchBookByKakaoPort, SearchBlogByKakaoPort {

    override fun searchBook(query: String, page: Int, size: Int): PageResult<Book> = runBlocking(Dispatchers.IO) {
        log.info { "@@ KakaoAdapter: searchBook query=$query page=$page size=$size" }

        async {
            val response: KakaoBookResponse = searchBookAPI(query, page, size)
            val bookList = response.documents.stream().map { kakaoMapper.toDomain(it) }.toList()
            PageResult(page, size, response.meta.totalCount, bookList);
        }.await()
    }

    private suspend fun searchBookAPI(query: String, page: Int, size: Int): KakaoBookResponse =
        withContext(dispatcher) { kakaoClient.searchBook(query, page, size) }

    override fun searchBlog(query: String, page: Int, size: Int): PageResult<Blog> = runBlocking(Dispatchers.IO) {
        log.info { "@@ KakaoAdapter: searchBlog query=$query page=$page size=$size" }

        async {
            val response: KakaoBlogResponse = searchBlogAPI(query, page, size)
            val blogList = response.documents.stream().map { kakaoMapper.toDomain(it) }.toList()

            PageResult(page, size, response.meta.totalCount, blogList);
        }.await()
    }

    private suspend fun searchBlogAPI(query: String, page: Int, size: Int): KakaoBlogResponse =
        withContext(dispatcher) { kakaoClient.searchBlog(query, page, size) }
}