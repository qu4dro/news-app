package ru.orlovvv.yandex.newsapp.repository

import ru.orlovvv.yandex.newsapp.api.RetrofitInstance

class NewsRepository {

    suspend fun getTopNews() = RetrofitInstance.api.getTopNews()
}