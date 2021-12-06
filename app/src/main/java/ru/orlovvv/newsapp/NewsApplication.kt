package ru.orlovvv.newsapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class NewsApplication : Application() {

    companion object {
        lateinit var instance: NewsApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        instance = this
    }

}