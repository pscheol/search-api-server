package com.search.application.blog.vo

import com.search.domain.entity.Blog
import java.time.LocalDate

data class BlogData(
    val title: String,
    val contents: String,
    val url: String,
    val blogName: String,
    val postDate: LocalDate,
) {
    constructor(
        src: Blog
    ) : this(
        title = src.title.value,
                contents = src.contents.value,
                url = src.url.url,
                blogName = src.blogName.name,
                postDate = src.postDate.parseLocalDate(),
    )
}
