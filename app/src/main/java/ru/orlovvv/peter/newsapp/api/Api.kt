package ru.orlovvv.peter.newsapp.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.orlovvv.peter.newsapp.models.news.NewsResponse
import ru.orlovvv.peter.newsapp.models.news_sources.SourceResponse
import ru.orlovvv.peter.newsapp.util.ApiKey.Companion.DEVELOPER_API_KEY
import ru.orlovvv.peter.newsapp.util.ApiKey.Companion.DEVELOPER_API_KEY2

interface Api {

    @GET("/v2/everything")
    suspend fun findNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String = DEVELOPER_API_KEY2
    ): Response<NewsResponse>


    @GET("v2/top-headlines")
    suspend fun getTopNews(
        @Query("country") country: String = "ru",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String = DEVELOPER_API_KEY2
    ): Response<NewsResponse>

    @GET("/v2/sources")
    suspend fun getAllSources(
        @Query("language") language: String = "ru",
        @Query("country") country: String = "ru",
        @Query("apiKey") apiKey: String = DEVELOPER_API_KEY2
    ) : Response<SourceResponse>
}