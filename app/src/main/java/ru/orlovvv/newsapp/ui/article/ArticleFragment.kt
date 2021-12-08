package ru.orlovvv.newsapp.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.newsapp.R
import ru.orlovvv.newsapp.adapters.ArticleAdapter
import ru.orlovvv.newsapp.data.model.Data
import ru.orlovvv.newsapp.databinding.FragmentArticleBinding
import ru.orlovvv.newsapp.utils.Resource
import ru.orlovvv.newsapp.viewmodels.CacheViewModel
import ru.orlovvv.newsapp.viewmodels.TrendingNewsViewModel
import timber.log.Timber

@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.fragment_article), ArticleAdapter.ArticleAdapterListener {

    private var _articleFragmentBinding: FragmentArticleBinding? = null
    val articleFragmentBinding
        get() = _articleFragmentBinding!!

    private val cacheViewModel: CacheViewModel by activityViewModels()
    private val trendingNewsViewModel: TrendingNewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _articleFragmentBinding = FragmentArticleBinding.inflate(inflater, container, false)
        return articleFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupUI()
    }

    private fun setupObservers() {
        cacheViewModel.selectedArticle.observe(viewLifecycleOwner, Observer {
            articleFragmentBinding.article = it
        })
        trendingNewsViewModel.similarNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    articleFragmentBinding.apply {
                        loadingIndicator.visibility = View.VISIBLE
                        groupSimilar.visibility = View.GONE
                    }
                }
                is Resource.Error -> {
                    articleFragmentBinding.apply {
                        loadingIndicator.visibility = View.VISIBLE
                        groupSimilar.visibility = View.GONE
                    }
                }
                is Resource.Success -> {
                    articleFragmentBinding.apply {
                        loadingIndicator.visibility = View.GONE
                        groupSimilar.visibility = View.VISIBLE
                    }
                    response.data?.let {
                        Timber.d(it.toString())
                    }
                }
            }
        })
    }

    private fun setupUI() {
        articleFragmentBinding.apply {
            lifecycleOwner = this@ArticleFragment
            trendViewModel = trendingNewsViewModel
            rvSimilarNews.adapter = ArticleAdapter(this@ArticleFragment, isSmall = true)
            fabSource.setOnClickListener {
                val link = cacheViewModel.selectedArticle.value?.url ?: ""
                val action =
                    ArticleFragmentDirections.actionArticleFragmentToSourceFragment(link)
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _articleFragmentBinding = null
    }

    override fun onArticleClick(cardView: View, article: Data) {

    }
}