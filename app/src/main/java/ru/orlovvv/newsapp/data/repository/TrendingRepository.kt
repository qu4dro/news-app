package ru.orlovvv.newsapp.data.repository

import ru.orlovvv.newsapp.data.api.NewsApiHelper
import ru.orlovvv.newsapp.data.db.CacheDao
import ru.orlovvv.newsapp.data.db.SavedDao
import javax.inject.Inject

class TrendingRepository @Inject constructor(
    private val apiHelper: NewsApiHelper,
    private val cacheDao: CacheDao,
    private val savedDao: SavedDao
) {

    suspend fun getTrendingNews() = apiHelper.getTrendingNews()

}