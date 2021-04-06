package ru.orlovvv.peter.newsapp.models.news

data class NewsResponse(
    val articles: MutableList<Article>?,
    val status: String?,
    val totalResults: Int?
)