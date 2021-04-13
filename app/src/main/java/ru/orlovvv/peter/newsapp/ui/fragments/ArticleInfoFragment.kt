package ru.orlovvv.peter.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.databinding.FragmentArticleInfoBinding

class ArticleInfoFragment : Fragment(R.layout.fragment_article_info) {

    val args: ArticleInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val article = args.article
        val binding = FragmentArticleInfoBinding.inflate(inflater)

        binding.wvArticleInfo.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            article.url?.let { loadUrl(it) }
        }
        return binding.root
    }
}