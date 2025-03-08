package com.search.adapter.primary.api.blog.dto

import com.search.application.blog.vo.BlogData
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "블로그 검색결과")
data class SearchBlog(
    @Schema(description = "제목", example = "저는 치킨 왕입니다.")
    val title: String,
    @Schema(description = "내용", example = "치킨 왕이 되고싶으십니까?...")
    val contents: String,
    @Schema(description = "경로", example = "https://test.com")
    val url: String,
    @Schema(description = "블로그명", example = "프로 치킨왕")
    val blogName: String,
    @Schema(description = "게시일", example = "2025-03-05")
    val postDate: LocalDate,
    ) {

    constructor(src : BlogData) : this
        (
        title = src.title,
        contents = src.contents,
        url = src.url,
        blogName = src.blogName,
        postDate = src.postDate,
    )

    companion object {
        fun of(blogData: List<BlogData>): List<SearchBlog> {
            return blogData.stream().map { SearchBlog(it) }.toList()
        }
    }
}