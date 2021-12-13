package ru.orlovvv.newsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.orlovvv.newsapp.data.model.Article

@Dao
interface CacheDao {

    @Query("SELECT * FROM articles WHERE isBookmarked = 0")
    fun getTrendingCache(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrendingCache(articlesList: List<Article>)

    @Query("DELETE FROM articles WHERE isBookmarked = 0")
    suspend fun deleteTrendingCache()

    @Transaction
    suspend fun updateCache(
        articlesList: List<Article>
    ) {
        deleteTrendingCache()
        insertTrendingCache(articlesList)
    }

}