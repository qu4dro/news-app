package ru.orlovvv.newsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.orlovvv.newsapp.data.model.Data
import javax.inject.Inject

@HiltViewModel
class CacheViewModel @Inject constructor(): ViewModel() {

    private val _selectedArticle = MutableLiveData<Data>()
    val selectedArticle
        get() = _selectedArticle

    fun selectArticle(article: Data) {
        _selectedArticle.value = article
    }

}