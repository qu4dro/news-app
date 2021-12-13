package ru.orlovvv.newsapp.data.api

import retrofit2.Response
import ru.orlovvv.newsapp.data.model.News
import javax.inject.Inject

class NewsApiHelperImpl @Inject constructor(private val api: NewsApi) : NewsApiHelper {

    override suspend fun getTopNews(): Response<News> = api.getTopNews()
    override suspend fun searchNews(searchQuery: String): Response<News> = api.searchNews(searchQuery)

}