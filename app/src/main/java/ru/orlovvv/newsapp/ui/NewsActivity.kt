package ru.orlovvv.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.orlovvv.newsapp.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private var _newsActivityBinding: ActivityNewsBinding? = null
    val newsActivityBinding
        get() = _newsActivityBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _newsActivityBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(newsActivityBinding.root)
    }
}