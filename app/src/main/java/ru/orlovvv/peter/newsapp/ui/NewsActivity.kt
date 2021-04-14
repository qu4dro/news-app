package ru.orlovvv.peter.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_news.*
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.db.NewsDatabase
import ru.orlovvv.peter.newsapp.repository.NewsRepository

class NewsActivity : AppCompatActivity() {

    private lateinit var _newsViewModel: NewsViewModel
    val newsViewModel: NewsViewModel
        get() = _newsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        supportActionBar?.hide()

        val newsDB = NewsDatabase(this)
        val newsRepository = NewsRepository(newsDB)
        val newsViewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        _newsViewModel =
            ViewModelProvider(this, newsViewModelProviderFactory).get(NewsViewModel::class.java)

        bn_menu.setupWithNavController(nav_host_fragment.findNavController())

        nav_host_fragment.findNavController()
            .addOnDestinationChangedListener { controller, destination, arguments ->
                if (destination.id == R.id.loadingFragment) {
                    bn_menu.visibility = View.GONE
                } else {
                    bn_menu.visibility = View.VISIBLE
                }
            }

    }
}