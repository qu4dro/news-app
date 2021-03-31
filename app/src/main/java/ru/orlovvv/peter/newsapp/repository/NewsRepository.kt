package ru.orlovvv.peter.newsapp.repository

import ru.orlovvv.peter.newsapp.api.RetrofitInstance

class NewsRepository {

    suspend fun getTopNews() = RetrofitInstance.api.getTopNews()
}