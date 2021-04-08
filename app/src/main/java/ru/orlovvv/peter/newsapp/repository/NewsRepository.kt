package ru.orlovvv.peter.newsapp.repository

import ru.orlovvv.peter.newsapp.api.RetrofitInstance
import ru.orlovvv.peter.newsapp.db.NewsDatabase
import ru.orlovvv.peter.newsapp.models.news.Article

class NewsRepository(val newsDB: NewsDatabase) {

    // network
    suspend fun getTopNews(page: Int) = RetrofitInstance.api.getTopNews(page)
    suspend fun getAllSources() = RetrofitInstance.api.getAllSources()
    suspend fun findNews(searchQuery: String, page: Int) = RetrofitInstance.api.findNews(searchQuery, page)

    // db
    suspend fun insert(article: Article) = newsDB.getNewsDao().insert(article)
    suspend fun delete(article: Article) = newsDB.getNewsDao().delete(article)
    fun getAll() = newsDB.getNewsDao().getAll()

}