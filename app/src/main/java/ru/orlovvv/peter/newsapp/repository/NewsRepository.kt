package ru.orlovvv.peter.newsapp.repository

import ru.orlovvv.peter.newsapp.api.Api
import ru.orlovvv.peter.newsapp.db.NewsDAO
import ru.orlovvv.peter.newsapp.db.NewsDatabase
import ru.orlovvv.peter.newsapp.models.news.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsDAO: NewsDAO, private val api: Api) {

    // network
    suspend fun getTopNews(page: Int) = api.getTopNews(page)
    suspend fun getAllSources() = api.getAllSources()
    suspend fun findNews(searchQuery: String, page: Int, sources: String) =
        api.findNews(searchQuery, page, sources)

    // db
    suspend fun insert(article: Article) = newsDAO.insert(article)
    suspend fun delete(article: Article) = newsDAO.delete(article)
    fun getAll() = newsDAO.getAll()
    suspend fun isExist(url: String) = newsDAO.isExist(url)

}