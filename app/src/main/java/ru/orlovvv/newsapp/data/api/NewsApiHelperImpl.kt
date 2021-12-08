package ru.orlovvv.newsapp.data.api

import retrofit2.Response
import ru.orlovvv.newsapp.data.model.TrendingNews
import javax.inject.Inject

class NewsApiHelperImpl @Inject constructor(private val api: NewsApi) : NewsApiHelper {

    override suspend fun getTrendingNews(): Response<TrendingNews> = api.getTrendingNews()
    override suspend fun getSimilarNews(uuid: String): Response<TrendingNews> =
        api.getSimilarNews(uuid)

}