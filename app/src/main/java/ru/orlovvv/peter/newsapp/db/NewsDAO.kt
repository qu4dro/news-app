package ru.orlovvv.peter.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.orlovvv.peter.newsapp.models.news.Article

@Dao
interface NewsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Delete
    suspend fun delete(article: Article)

    @Query(value = "SELECT * FROM articles")
    fun getAll(): LiveData<List<Article>>
}