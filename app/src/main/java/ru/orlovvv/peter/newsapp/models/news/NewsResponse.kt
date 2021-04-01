package ru.orlovvv.peter.newsapp.models.news

data class NewsResponse(
    val articles: List<Article>?,
    val status: String?,
    val totalResults: Int?
)