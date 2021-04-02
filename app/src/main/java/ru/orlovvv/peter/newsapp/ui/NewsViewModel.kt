package ru.orlovvv.peter.newsapp.ui

import android.util.Log
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

    private val _topNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val topNews: LiveData<Resource<NewsResponse>>
        get() = _topNews

    private val _topNewsArticlesList: MutableLiveData<List<Article>> = MutableLiveData()
    val topNewsArticlesList: LiveData<List<Article>>
        get() = _topNewsArticlesList

    private val _searchedNewsArticlesList: MutableLiveData<List<Article>> = MutableLiveData()
    val searchedNewsArticlesList: LiveData<List<Article>>
        get() = _searchedNewsArticlesList

    init {
        getTopNews()
    }

    private fun getTopNews() = viewModelScope.launch {
        try {
            _topNewsArticlesList.value = newsRepository.getTopNews().body()?.articles
        } catch (e: Exception) {
            _topNewsArticlesList.value = ArrayList()
        }
    }

    fun findNews(searchQuery: String) = viewModelScope.launch {
        try {
            _searchedNewsArticlesList.value = newsRepository.findNews(searchQuery).body()?.articles
        } catch (e: Exception) {
            _searchedNewsArticlesList.value = ArrayList()
        }
    }

    private fun handleResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}

