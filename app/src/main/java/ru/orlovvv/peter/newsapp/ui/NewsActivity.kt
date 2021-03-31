package ru.orlovvv.peter.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_news.*
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.repository.NewsRepository

class NewsActivity : AppCompatActivity() {

    private lateinit var _newsViewModel: NewsViewModel
    val newsViewModel: NewsViewModel
        get() = _newsViewModel
    private lateinit var newsRepository: NewsRepository
    private lateinit var newsViewModelProviderFactory: NewsViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        supportActionBar?.hide()

        newsRepository = NewsRepository()
        newsViewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        _newsViewModel =
            ViewModelProvider(this, newsViewModelProviderFactory).get(NewsViewModel::class.java)

        bn_menu.setupWithNavController(nav_host_fragment.findNavController())
    }
}