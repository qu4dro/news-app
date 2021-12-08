package ru.orlovvv.newsapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.orlovvv.newsapp.data.model.Article
import ru.orlovvv.newsapp.data.model.News
import ru.orlovvv.newsapp.data.repository.TrendingRepository
import ru.orlovvv.newsapp.utils.NetworkHelper
import ru.orlovvv.newsapp.utils.Resource
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val trendingRepository: TrendingRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _topNews = MutableLiveData<Resource<News>>()
    val topNews: LiveData<Resource<News>>
        get() = _topNews

    private val _selectedArticle = MutableLiveData<Article>()
    val selectedArticle
        get() = _selectedArticle

    init {
        getTopNewsFromServer()
    }

    fun getTopNewsFromServer() = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (networkHelper.isNetworkConnected()) {
                _topNews.postValue(Resource.Loading())
                val response = trendingRepository.getTopNews()
                _topNews.postValue(handleTopNewsResponse(response))
            } else {
                _topNews.postValue(Resource.Error("Check internet connection"))
            }
        } catch (e: Exception) {
            _topNews.postValue(Resource.Error("Can't get news: ${e.message}"))
        }
    }

    private fun handleTopNewsResponse(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.errorBody().toString())
    }

    fun selectArticle(article: Article) {
        _selectedArticle.value = article
    }

}