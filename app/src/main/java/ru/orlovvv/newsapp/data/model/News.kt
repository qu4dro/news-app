package ru.orlovvv.newsapp.data.model

data class News(
    var articles: List<Article>,
    var status: String,
    var totalResults: Int
)