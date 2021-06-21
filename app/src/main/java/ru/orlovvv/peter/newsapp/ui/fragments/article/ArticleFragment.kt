package ru.orlovvv.peter.newsapp.ui.fragments.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.databinding.FragmentArticleBinding

class ArticleFragment : Fragment(R.layout.fragment_article) {

    val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val article = args.article
        val binding = FragmentArticleBinding.inflate(inflater)

        binding.wvArticleInfo.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            article.url?.let { loadUrl(it) }
        }
        return binding.root
    }
}