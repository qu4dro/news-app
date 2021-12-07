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
import java.lang.Error
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class TrendingNewsViewModel @Inject constructor(
    private val trendingRepository: TrendingRepository,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private val _trending = MutableLiveData<Resource<TrendingNews>>()
    val trendingNews: LiveData<Resource<TrendingNews>>
        get() = _trending

    init {
        getTrendingNews()
    }

    fun getTrendingNews() = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (networkHelper.isNetworkConnected()) {
                _trending.postValue(Resource.Loading())
                val response = trendingRepository.getTrendingNews()
                _trending.postValue(handleTrendingNewsResponse(response))
            } else {
                _trending.postValue(Resource.Error("Check internet connection"))
            }
        } catch (e: Exception) {
            _trending.postValue(Resource.Error("Can't get news: ${e.message}"))
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

}