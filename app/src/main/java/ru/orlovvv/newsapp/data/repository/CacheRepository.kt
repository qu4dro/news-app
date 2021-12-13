package ru.orlovvv.newsapp.data.repository

import ru.orlovvv.newsapp.data.db.CacheDao
import ru.orlovvv.newsapp.data.model.Article
import javax.inject.Inject

class CacheRepository @Inject constructor(
    private val cacheDao: CacheDao
) {

    suspend fun updateCache(articlesList: List<Article>) = cacheDao.updateCache(articlesList)

    fun getTrendingCache() = cacheDao.getTrendingCache()

}