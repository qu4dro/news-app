package ru.orlovvv.newsapp.data.repository

import ru.orlovvv.newsapp.data.db.SavedDao
import ru.orlovvv.newsapp.data.model.Article
import javax.inject.Inject

class SavedRepository @Inject constructor(
    private val savedDao: SavedDao
) {

    suspend fun insertBookmarkArticle(article: Article) = savedDao.insertBookmarkArticle(article)

    suspend fun deleteBookmarkArticle(article: Article) = savedDao.deleteBookmarkArticle(article.url)

    fun getAllBookmarkArticles() = savedDao.getAllBookmarkArticles()

}