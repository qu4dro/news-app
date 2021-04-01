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

    val _topNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val topNews: LiveData<Resource<NewsResponse>>
        get() = _topNews

    private val _topNewsArticlesList: MutableLiveData<List<Article>> = MutableLiveData()
    val topNewsArticlesList: LiveData<List<Article>>
        get() = _topNewsArticlesList

    init {
        getTopNews()

    }

    private fun getTopNews() = viewModelScope.launch {
        try {
//            _topNews.value = Resource.Loading()
            _topNewsArticlesList.value = newsRepository.getTopNews().body()?.articles
//            val response = newsRepository.getTopNews()
//            _topNews.value = handleResponse(response)
//            Log.d("123", "getTopNews: перед присваиванием ${_topNewsArticlesList.value}")
//            _topNewsArticlesList.value = _topNews.value!!.data?.articles
//            Log.d("123", "getTopNews: после присваивания ${_topNewsArticlesList.value}")
        } catch (e: Exception) {
            _topNewsArticlesList.value = ArrayList()
        }

        //TODO TRY CATCH

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

