package ru.orlovvv.peter.newsapp.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.orlovvv.peter.newsapp.models.news.NewsResponse
import ru.orlovvv.peter.newsapp.models.news_sources.SourceResponse
import ru.orlovvv.peter.newsapp.util.ApiKey.Companion.DEVELOPER_API_KEY
import ru.orlovvv.peter.newsapp.util.ApiKey.Companion.DEVELOPER_API_KEY2
import ru.orlovvv.peter.newsapp.util.Constants.Companion.PAGE_SIZE

interface Api {

    @GET("/v2/everything")
    suspend fun findNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int,
        @Query("sources") sources: String,
        @Query("pageSize") pageSize: Int = PAGE_SIZE,
        @Query("apiKey") apiKey: String = DEVELOPER_API_KEY2
    ): Response<NewsResponse>


    @GET("v2/top-headlines")
    suspend fun getTopNews(
        @Query("page") page: Int,
        @Query("country") country: String = "ru",
        @Query("pageSize") pageSize: Int = PAGE_SIZE,
        @Query("apiKey") apiKey: String = DEVELOPER_API_KEY2
    ): Response<NewsResponse>

    @GET("/v2/sources")
    suspend fun getAllSources(
        @Query("language") language: String = "ru",
        @Query("country") country: String = "ru",
        @Query("apiKey") apiKey: String = DEVELOPER_API_KEY2
    ) : Response<SourceResponse>
}