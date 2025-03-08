package com.search.adapter.infra.kakao

import com.search.adapter.infra.kakao.feign.KakaoClient
import com.search.adapter.infra.kakao.feign.dto.KakaoBlogResponse
import com.search.adapter.infra.kakao.feign.dto.KakaoBookResponse
import com.search.application.blog.output.SearchBlogByKakaoPort
import com.search.application.book.output.SearchBookByKakaoPort
import com.search.domain.entity.Blog
import com.search.domain.entity.Book
import com.search.dto.PageResult
import org.springframework.stereotype.Service

@Service
class KakaoAdapter(
    private val kakaoClient: KakaoClient,
    private val kakaoMapper: KakaoMapper
) : SearchBookByKakaoPort, SearchBlogByKakaoPort {
    override fun searchBook(query: String, page: Int, size: Int): PageResult<Book> {
        val response: KakaoBookResponse = kakaoClient.searchBook(query, page, size)
        val bookList = response.documents.stream().map { kakaoMapper.toDomain(it) }.toList()

        return PageResult(page, size, response.meta.totalCount, bookList);
    }

    override fun searchBlog(query: String, page: Int, size: Int): PageResult<Blog> {
        val response: KakaoBlogResponse = kakaoClient.searchBlog(query, page, size)
        val blogList = response.documents.stream().map { kakaoMapper.toDomain(it) }.toList()

        return PageResult(page, size, response.meta.totalCount, blogList);
    }
}