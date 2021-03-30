package ru.orlovvv.yandex.newsapp.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.orlovvv.yandex.newsapp.models.NewsResponse
import ru.orlovvv.yandex.newsapp.util.ApiKey.Companion.DEVELOPER_API_KEY

interface Api {

    @GET("v2/top-headlines")
    suspend fun getTopNews(
        @Query("country") country: String = "ru",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String = DEVELOPER_API_KEY
    ): Response<NewsResponse>

}