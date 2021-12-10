package ru.orlovvv.newsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.orlovvv.newsapp.data.model.Article

@Dao
interface SavedDao {

    @Query("SELECT * FROM articles WHERE isBookmarked = 1")
    fun getAllBookmarkArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkArticle(article: Article)

    @Query("DELETE FROM articles WHERE url = :url")
    suspend fun deleteBookmarkArticle(url: String)

}