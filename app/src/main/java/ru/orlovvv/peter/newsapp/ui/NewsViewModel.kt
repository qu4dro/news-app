package ru.orlovvv.peter.newsapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.orlovvv.peter.newsapp.models.NewsResponse
import ru.orlovvv.peter.newsapp.repository.NewsRepository
import ru.orlovvv.peter.newsapp.util.Resource

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

    private val _topNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val topNews: LiveData<Resource<NewsResponse>>
        get() = _topNews


    init {
        getTopNews()
    }


    private fun getTopNews() = viewModelScope.launch {
        _topNews.value = Resource.Loading()
        val response = newsRepository.getTopNews()
        _topNews.value = handleResponse(response)
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

