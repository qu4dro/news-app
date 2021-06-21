package ru.orlovvv.peter.newsapp.ui.fragments.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.databinding.FragmentWebSourceBinding

class WebSourceFragment : Fragment(R.layout.fragment_web_source) {

    private val args: WebSourceFragmentArgs by navArgs()
    private var _binding: FragmentWebSourceBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWebSourceBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.article
        binding.wvArticleWebSource.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(article.url)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}