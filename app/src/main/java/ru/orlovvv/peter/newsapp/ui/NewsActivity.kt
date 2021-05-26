package ru.orlovvv.peter.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_news.*
import ru.orlovvv.peter.newsapp.R

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private val _newsViewModel: NewsViewModel by viewModels ()
    val newsViewModel: NewsViewModel
        get() = _newsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        supportActionBar?.hide()

        bn_menu.setupWithNavController(nav_host_fragment.findNavController())

        nav_host_fragment.findNavController()
            .addOnDestinationChangedListener { controller, destination, arguments ->
                if (destination.id == R.id.loadingFragment || destination.id == R.id.articleInfoFragment) {
                    bn_menu.visibility = View.GONE
                } else {
                    bn_menu.visibility = View.VISIBLE
                }
            }

    }
}