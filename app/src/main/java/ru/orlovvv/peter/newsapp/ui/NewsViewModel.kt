package ru.orlovvv.peter.newsapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.orlovvv.peter.newsapp.repository.NewsRepository

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel(){

    init {
        Log.d("123", ": viewModel")
    }


    private fun getTopNews() = viewModelScope.launch {

    }
}

