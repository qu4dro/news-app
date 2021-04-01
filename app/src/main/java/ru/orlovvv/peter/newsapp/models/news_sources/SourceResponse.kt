package ru.orlovvv.peter.newsapp.models.news_sources

data class SourceResponse(
    val sources: List<NewsSourceInfo>,
    val status: String
)