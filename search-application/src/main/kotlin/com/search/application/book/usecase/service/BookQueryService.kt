package com.search.application.book.usecase.service

import com.search.application.book.output.SearchBookByKakaoPort
import com.search.application.book.output.SearchBookByNaverPort
import com.search.domain.entity.Book
import com.search.dto.PageResult
import com.search.logger.DefaultLogger
import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Service

@Service
class BookQueryService(
    private val searchBookByKakaoPort: SearchBookByKakaoPort,
    private val searchBookByNaverPort: SearchBookByNaverPort
) : DefaultLogger {

    @CircuitBreaker(name = "naverSearchBook", fallbackMethod = "searchBookFallBack")
    fun search(query: String, page: Int, size: Int) : PageResult<Book> {
        log.info { "BookQueryService::search query=$query, page=$page, size=$size" }
        return searchBookByNaverPort.searchBook(query, page, size)
    }

    /**
     * circuitBreaker failBack
     */
    fun searchBookFallBack(query: String, page: Int, size: Int, throwable: Throwable): PageResult<Book> {
        if (throwable is CallNotPermittedException) {
            return handleOpenCircuitByKakaoBook(query, page, size)
        }
        return handleException(query, page, size, throwable)
    }

    private fun handleOpenCircuitByKakaoBook(query: String, page: Int, size: Int) : PageResult<Book> {
        log.info { "## [BookQueryService] ::handleOpenCircuitByKakaoBook query=$query, page=$page, size=$size" }
        return searchBookByKakaoPort.searchBook(query, page, size)
    }

    private fun handleException(query: String, page: Int, size: Int, th: Throwable): PageResult<Book> {
        log.error(th) {"## [BookQueryService] ::An error occurred! Fallback to kakao search Book."}

        return searchBookByKakaoPort.searchBook(query, page, size)
    }
}