package ru.orlovvv.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import ru.orlovvv.newsapp.R
import ru.orlovvv.newsapp.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private var _newsActivityBinding: ActivityNewsBinding? = null
    val newsActivityBinding
        get() = _newsActivityBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _newsActivityBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(newsActivityBinding.root)
        setNavigation()
    }

    private fun setNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        newsActivityBinding.bottomNavigation.apply {
            setupWithNavController(navController)
            setOnItemReselectedListener { }
        }
    }
}