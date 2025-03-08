package com.search.application.blog.usecase.service

import com.search.application.blog.output.SearchBlogByKakaoPort
import com.search.application.blog.output.SearchBlogByNaverPort
import com.search.domain.entity.Blog
import com.search.dto.PageResult
import com.search.logger.DefaultLogger
import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Service

@Service
class BlogQueryService(
    private val searchBlogByNaverPort: SearchBlogByNaverPort,
    private val searchBlogByKakaoPort: SearchBlogByKakaoPort,
) : DefaultLogger {

    @CircuitBreaker(name = "naverSearchBlog", fallbackMethod = "searchBlogFallBack")
    fun search(query: String, page: Int, size: Int) : PageResult<Blog> {
        log.info { "## [BlogQueryService] ::search query=$query, page=$page, size=$size" }
        return searchBlogByNaverPort.searchBlog(query, page, size)
    }

    /**
     * circuitBreaker failBack
     */
    fun searchBlogFallBack(query: String, page: Int, size: Int, throwable: Throwable): PageResult<Blog> {
        if (throwable is CallNotPermittedException) {
            return handleOpenCircuitByKakaoBlog(query, page, size)
        }
        return handleException(query, page, size, throwable)
    }

    private fun handleOpenCircuitByKakaoBlog(query: String, page: Int, size: Int) : PageResult<Blog> {
        log.info { "## [BlogQueryService] ::handleOpenCircuitByKakaoBlog query=$query, page=$page, size=$size" }
        return searchBlogByKakaoPort.searchBlog(query, page, size)
    }

    private fun handleException(query: String, page: Int, size: Int, th: Throwable): PageResult<Blog> {
        log.error(th) {"## [BlogQueryService] ::An error occurred! Fallback to kakao search blog."}

        return searchBlogByKakaoPort.searchBlog(query, page, size)
    }
}