package ru.orlovvv.newsapp.ui

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.newsapp.R
import ru.orlovvv.newsapp.databinding.ActivityNewsBinding
import ru.orlovvv.newsapp.utils.NetworkHelper
import ru.orlovvv.newsapp.workers.NetworkBroadcastReceiver
import timber.log.Timber
import java.lang.Exception

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var networkReceiver: NetworkBroadcastReceiver
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var _newsActivityBinding: ActivityNewsBinding? = null
    val newsActivityBinding
        get() = _newsActivityBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _newsActivityBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(newsActivityBinding.root)
        setNavigation()
        setNetworkReceiver()
    }

    private fun setNavigation() {

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.trendingFragment, R.id.savedFragment, R.id.searchFragment),
        )

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        newsActivityBinding.toolbar.apply {
            setSupportActionBar(this)
        }

        newsActivityBinding.bottomNavigation.apply {
            setupActionBarWithNavController(navController, appBarConfiguration)
            setupWithNavController(navController)
            setOnItemReselectedListener { }
        }
    }

    private fun setNetworkReceiver() {
        val snack = makeNetworkSnack()
        networkReceiver = object : NetworkBroadcastReceiver() {
            override fun onNetworkChange() {
                val status = NetworkHelper(this@NewsActivity).isNetworkConnected()
                if (!status) {
                    snack.show()
                } else {
                    snack.dismiss()
                }
            }

        }
    }

    private fun registerNetworkReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, intentFilter)
    }

    private fun unregisterNetworkReceiver() {
        try {
            unregisterReceiver(networkReceiver)
        } catch (e: Exception) {
            Timber.d("${e.stackTrace}")
        }
    }

    private fun makeNetworkSnack(): Snackbar =
        Snackbar.make(
            findViewById(R.id.nav_host_fragment),
            R.string.check_connection,
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction(R.string.settings) {
                startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS));
                this.dismiss()
            }
            anchorView = newsActivityBinding.bottomNavigation
        }

    override fun onResume() {
        super.onResume()
        registerNetworkReceiver()
    }

    override fun onPause() {
        unregisterNetworkReceiver()
        super.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}