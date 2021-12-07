package ru.orlovvv.newsapp.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.orlovvv.newsapp.data.model.TrendingNews
import ru.orlovvv.newsapp.utils.ApiKey.API_KEY

interface NewsApi {

    @GET("news/top")
    suspend fun getTrendingNews(
        @Query("api_token") api_token: String = API_KEY,
        @Query("locale") locale: String = "ru",
        @Query("limit") limit: Int = 5,
        @Query("page") page: Int = 1
    ): Response<TrendingNews>

}