package ru.orlovvv.peter.newsapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.databinding.ActivityNewsBinding


@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private val _newsViewModel: NewsViewModel by viewModels()
    val newsViewModel: NewsViewModel
        get() = _newsViewModel
    private var _binding: ActivityNewsBinding? = null
    val binding
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavController()
    }

    private fun setupNavController() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        binding.bnMenu.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loadingFragment || destination.id == R.id.articleFragment || destination.id == R.id.webSourceFragment) {
                binding.bnMenu.visibility = View.GONE
            } else {
                binding.bnMenu.visibility = View.VISIBLE
                slideMenu()
            }

        }
    }

    private fun slideMenu() {
        val layoutParams = binding.bnMenu.layoutParams
        if (layoutParams is CoordinatorLayout.LayoutParams) {
            val behavior = layoutParams.behavior
            if (behavior is HideBottomViewOnScrollBehavior<*>) {
                val hideShowBehavior =
                    behavior as HideBottomViewOnScrollBehavior<BottomNavigationView>
                hideShowBehavior.slideUp(binding.bnMenu)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}