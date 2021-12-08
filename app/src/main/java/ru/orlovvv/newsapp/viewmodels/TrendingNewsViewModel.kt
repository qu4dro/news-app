package ru.orlovvv.newsapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.orlovvv.newsapp.data.model.TrendingNews
import ru.orlovvv.newsapp.data.repository.TrendingRepository
import ru.orlovvv.newsapp.utils.NetworkHelper
import ru.orlovvv.newsapp.utils.Resource
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class TrendingNewsViewModel @Inject constructor(
    private val trendingRepository: TrendingRepository,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private val _trendingNews = MutableLiveData<Resource<TrendingNews>>()
    val trendingNews: LiveData<Resource<TrendingNews>>
        get() = _trendingNews

    private val _similarNews = MutableLiveData<Resource<TrendingNews>>()
    val similarNews: LiveData<Resource<TrendingNews>>
        get() = _similarNews

    init {
        getTrendingNewsFromServer()
    }

    fun getTrendingNewsFromServer() = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (networkHelper.isNetworkConnected()) {
                _trendingNews.postValue(Resource.Loading())
                val response = trendingRepository.getTrendingNews()
                _trendingNews.postValue(handleTrendingNewsResponse(response))
            } else {
                _trendingNews.postValue(Resource.Error("Check internet connection"))
            }
        } catch (e: Exception) {
            _trendingNews.postValue(Resource.Error("Can't get news: ${e.message}"))
        }
    }

    private fun handleTrendingNewsResponse(response: Response<TrendingNews>): Resource<TrendingNews> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.errorBody().toString())
    }

    fun getSimilarNewsFromServer(uuid: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (networkHelper.isNetworkConnected()) {
                _similarNews.postValue(Resource.Loading())
                val response = trendingRepository.getSimilarNews(uuid)
                _similarNews.postValue(handleSimilarNewsResponse(response))
            } else {
                _similarNews.postValue(Resource.Error("Check internet connection"))
            }
        } catch (e: Exception) {
            _similarNews.postValue(Resource.Error("Can't get news: ${e.message}"))
        }
    }

    private fun handleSimilarNewsResponse(response: Response<TrendingNews>): Resource<TrendingNews> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.errorBody().toString())
    }

}