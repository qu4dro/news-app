package ru.orlovvv.peter.newsapp.ui

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.orlovvv.peter.newsapp.models.news.Article
import ru.orlovvv.peter.newsapp.models.news.NewsResponse
import ru.orlovvv.peter.newsapp.models.news_sources.NewsSourceInfo
import ru.orlovvv.peter.newsapp.repository.NewsRepository
import ru.orlovvv.peter.newsapp.util.Resource
import java.lang.Exception

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private var topNewsResponse: NewsResponse? = null
    var currentTopNewsPage = 1

    private val _topNewsArticlesList: MutableLiveData<List<Article>> = MutableLiveData()
    val topNewsArticlesList: LiveData<List<Article>>
        get() = _topNewsArticlesList

    private val _searchedNewsArticlesList: MutableLiveData<List<Article>> = MutableLiveData()
    val searchedNewsArticlesList: LiveData<List<Article>>
        get() = _searchedNewsArticlesList

    private var _savedNewsArticlesList: LiveData<List<Article>> = MutableLiveData()
    val savedNewsArticlesList: LiveData<List<Article>>
        get() = _savedNewsArticlesList


    init {
        getAllSavedNews()
        getTopNews(currentTopNewsPage)
    }

    private fun getTopNews(page: Int) = viewModelScope.launch {
        try {
            val response = newsRepository.getTopNews(page)
            if (response.isSuccessful) {
                response.body()?.let {
                    currentTopNewsPage++
                    if (topNewsResponse == null) {
                        topNewsResponse = it
                    } else {
                        val oldTopNews = topNewsResponse?.articles
                        val newTopNews = it.articles
                        if (newTopNews != null) {
                            oldTopNews?.addAll(newTopNews)
                        }
                    }
                    _topNewsArticlesList.value = topNewsResponse?.articles ?: it.articles
                }
            }
        } catch (e: Exception) {
            _topNewsArticlesList.value = ArrayList()
        }
    }



    fun saveToReadLater(article: Article) = viewModelScope.launch {
        newsRepository.insert(article)
    }

    fun deleteFromReadLater(article: Article) = viewModelScope.launch {
        newsRepository.delete(article)
    }

    fun getAllSavedNews()  {
        _savedNewsArticlesList = newsRepository.getAll()
    }



    fun findNews(searchQuery: String) = viewModelScope.launch {
        try {
            _searchedNewsArticlesList.value = newsRepository.findNews(searchQuery).body()?.articles
        } catch (e: Exception) {
            _searchedNewsArticlesList.value = ArrayList()
        }
    }

}



