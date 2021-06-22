package ru.orlovvv.peter.newsapp.ui.fragments.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.databinding.FragmentWebSourceBinding
import ru.orlovvv.peter.newsapp.ui.NewsViewModel
import timber.log.Timber

class WebSourceFragment : Fragment(R.layout.fragment_web_source) {

    private val newsViewModel: NewsViewModel by activityViewModels()
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

        newsViewModel.articleIsExist.observe(viewLifecycleOwner, Observer {
            Timber.d("${newsViewModel.articleIsExist.value}")
            binding.toolbar.menu.findItem(R.id.deleteArticle).isVisible = it
            binding.toolbar.menu.findItem(R.id.saveArticle).isVisible = !it
        })

        newsViewModel.isExist(article.url)
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            wvArticleWebSource.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(article.url)
            }
        }

        setToolbarActions()
    }

    private fun setToolbarActions() {
        binding.toolbar.apply {

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.saveArticle -> {
                        newsViewModel.saveToReadLater(args.article)
                        true
                    }
                    R.id.shareArticle -> {
                        val intent = Intent(Intent.ACTION_SEND).setType("text/plain")
                            .putExtra(Intent.EXTRA_TEXT, args.article.url)
                        startActivity(intent)
                        true
                    }
                    R.id.deleteArticle -> {
                        newsViewModel.deleteFromReadLater(args.article)
                        findNavController().navigateUp()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}