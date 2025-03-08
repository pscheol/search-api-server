package com.search.adapter.infra.kakao

import com.search.adapter.infra.kakao.feign.dto.KakaoResponseData
import com.search.domain.entity.Blog
import com.search.domain.entity.Book
import org.springframework.stereotype.Component

@Component
class KakaoMapper {

    fun toDomain(src: KakaoResponseData.BlogDocument): Blog {
        return Blog(
            title = src.title,
            url = src.url,
            contents = src.contents,
            blogName = src.blogName,
            postDate = src.datetime,
        )
    }

    fun toDomain(src: KakaoResponseData.BookDocument): Book {
        return Book(
            title = src.title,
            author = src.authors[0],
            publisher = src.publisher,
            datetime = src.datetime,
            isbn = src.isbn,
        )
    }
}