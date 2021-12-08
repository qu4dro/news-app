package ru.orlovvv.newsapp.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.orlovvv.newsapp.data.model.News
import ru.orlovvv.newsapp.utils.ApiKey.API_KEY

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopNews(
        @Query("page") page: Int = 1,
        @Query("country") country: String = "ru",
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<News>


}