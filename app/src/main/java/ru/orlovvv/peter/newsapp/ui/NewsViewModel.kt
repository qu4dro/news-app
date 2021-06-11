package ru.orlovvv.peter.newsapp.ui

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.orlovvv.peter.newsapp.models.news.Article
import ru.orlovvv.peter.newsapp.models.news.NewsResponse
import ru.orlovvv.peter.newsapp.models.news_sources.NewsSourceInfo
import ru.orlovvv.peter.newsapp.repository.NewsRepository
import ru.orlovvv.peter.newsapp.util.Resource
import java.lang.Error
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    var isFirstFeedOpen = true

    val categoriesList =
        listOf("Business", "General", "Health", "Science", "Sports", "Technology", "Entertainment")

    val checkedSources = mutableListOf<String>()

    private var topNewsResponse: NewsResponse? = null
    private var searchedNewsResponse: NewsResponse? = null
    var currentTopNewsPage = 1
    var currentSearchNewsPage = 1

    private val _topNewsArticlesList: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val topNewsArticlesList: LiveData<Resource<NewsResponse>>
        get() = _topNewsArticlesList

    private val _searchedNewsArticlesList: MutableLiveData<List<Article>> = MutableLiveData()
    val searchedNewsArticlesList: LiveData<List<Article>>
        get() = _searchedNewsArticlesList

    private var _savedNewsArticlesList: LiveData<List<Article>> = MutableLiveData()
    val savedNewsArticlesList: LiveData<List<Article>>
        get() = _savedNewsArticlesList

    private val _sourcesList: MutableLiveData<List<NewsSourceInfo>> = MutableLiveData()
    val sourceList: LiveData<List<NewsSourceInfo>>
        get() = _sourcesList

    init {
        getAllSavedNews()
//        getTopNews()
        getAllSources()
    }

    fun getTopNews() = viewModelScope.launch {
        try {
            Log.d("123", "LOADING: ")
            _topNewsArticlesList.value = Resource.Loading()
            val response = newsRepository.getTopNews(currentSearchNewsPage)
            _topNewsArticlesList.value = handleTopNewsResponse(response)
        } catch (e: Exception) {
            _topNewsArticlesList.value = Resource.Success(NewsResponse(ArrayList(), "Success", 0))
        }

    }

    private fun handleTopNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
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
                Log.d("123", "SUCCES: ${Resource.Success(topNewsResponse ?: it)}")
                return Resource.Success(topNewsResponse ?: it)
            }
        }
        Log.d("123", "NOT SUCCES:)}")
        return Resource.Error(response.message())
    }


    fun findNews(searchQuery: String, sources: List<String>) = viewModelScope.launch {
        try {
            Log.d(
                "123",
                "findNews: ${_searchedNewsArticlesList.value?.size} $currentSearchNewsPage"
            )
            val t = sources.toString().replace(", ", ",").replace("[", "").replace("]", "")
            val response = newsRepository.findNews(
                searchQuery,
                currentSearchNewsPage,
                TextUtils.join(",", sources)
            )
            Log.d("123", "findNews: $t")
            if (response.isSuccessful) {
                response.body()?.let {
                    currentSearchNewsPage++
                    if (searchedNewsResponse == null) {
                        searchedNewsResponse = it
                    } else {
                        val oldSearchedNews = searchedNewsResponse?.articles
                        val newSearchedNews = it.articles
                        if (newSearchedNews != null) {
                            oldSearchedNews?.addAll(newSearchedNews)
                            oldSearchedNews?.toList()
                        }
                    }
                    _searchedNewsArticlesList.value = searchedNewsResponse?.articles ?: it.articles
                }
            }
        } catch (e: Exception) {
            _searchedNewsArticlesList.value = ArrayList()
        }
    }

    fun getAllSources() = viewModelScope.launch {
        try {
            val response = newsRepository.getAllSources()
            if (response.isSuccessful) {
                response.body()?.let {
                    _sourcesList.value = response.body()!!.sources
                    Log.d("sss", "getAllSources: ${_sourcesList.value.toString()}")
                }
            }
        } catch (e: Exception) {
            _sourcesList.value = ArrayList()
        }
    }

    fun saveToReadLater(article: Article) = viewModelScope.launch {
        newsRepository.insert(article)
    }

    fun deleteFromReadLater(article: Article) = viewModelScope.launch {
        newsRepository.delete(article)
    }

    fun getAllSavedNews() {
        _savedNewsArticlesList = newsRepository.getAll()
    }

    fun resetArticlesList() {
        _searchedNewsArticlesList.value = ArrayList()
        searchedNewsResponse = null
        currentSearchNewsPage = 1
    }
}



