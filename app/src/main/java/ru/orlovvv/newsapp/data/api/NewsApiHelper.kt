package ru.orlovvv.newsapp.data.api

import retrofit2.Response
import ru.orlovvv.newsapp.data.model.News

interface NewsApiHelper {

    suspend fun getTopNews(): Response<News>

    suspend fun searchNews(searchQuery: String): Response<News>

}