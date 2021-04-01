package ru.orlovvv.peter.newsapp.repository

import ru.orlovvv.peter.newsapp.api.RetrofitInstance

class NewsRepository {

    suspend fun getTopNews() = RetrofitInstance.api.getTopNews()
    suspend fun getAllSources() = RetrofitInstance.api.getAllSources()
    suspend fun findNews(searchQuery: String) = RetrofitInstance.api.findNews(searchQuery)

}