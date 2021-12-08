package ru.orlovvv.newsapp.data.api

import retrofit2.Response
import ru.orlovvv.newsapp.data.model.TrendingNews

interface NewsApiHelper {

    suspend fun getTrendingNews(): Response<TrendingNews>
    suspend fun getSimilarNews(uuid: String): Response<TrendingNews>
}