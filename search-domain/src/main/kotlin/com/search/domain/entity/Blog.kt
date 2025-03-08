package com.search.domain.entity

import com.search.domain.vo.blog.*
import java.io.Serializable

class Blog(
    val title: BlogTitle,
    val contents: Contents,
    val url: BlogURL,
    val blogName: BlogName,
    val postDate: PostDate,
) : Serializable {

    constructor(
        title: String,
        contents: String,
        url: String,
        blogName: String,
        postDate: String,
    ) : this(
        title = BlogTitle(title),
        contents = Contents(contents),
        url = BlogURL(url),
        blogName = BlogName(blogName),
        postDate = PostDate(postDate),
    )


    override fun toString(): String {
        return "Blog(title=$title, contents=$contents, url=$url, blogName=$blogName, postDate=$postDate)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Blog) return false

        if (title != other.title) return false
        if (contents != other.contents) return false
        if (url != other.url) return false
        if (blogName != other.blogName) return false
        if (postDate != other.postDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + contents.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + blogName.hashCode()
        result = 31 * result + postDate.hashCode()
        return result
    }


}