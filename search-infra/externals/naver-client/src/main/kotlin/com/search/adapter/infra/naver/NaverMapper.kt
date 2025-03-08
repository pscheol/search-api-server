package com.search.adapter.infra.naver

import com.search.adapter.infra.naver.feign.dto.NaverResponseData
import com.search.domain.entity.Blog
import com.search.domain.entity.Book
import org.springframework.stereotype.Component

@Component
class NaverMapper {

    fun toDomain(src: NaverResponseData.BlogItem): Blog {
        return Blog(
            title = src.title,
            url = src.link,
            contents = src.description,
            blogName = src.bloggerName,
            postDate = src.postdate,
        )
    }

    fun toDomain(src: NaverResponseData.BookItem): Book {
        return Book(
            title = src.title,
            author = src.author,
            publisher = src.publisher,
            datetime = src.pubdate,
            isbn = src.isbn,
        )
    }
}