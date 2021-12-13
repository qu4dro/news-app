package ru.orlovvv.newsapp.ui.trending

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
import ru.orlovvv.newsapp.data.model.Article
import ru.orlovvv.newsapp.databinding.FragmentTrendingBinding
import ru.orlovvv.newsapp.utils.Resource
import ru.orlovvv.newsapp.viewmodels.NewsViewModel

@AndroidEntryPoint
class TrendingFragment : Fragment(R.layout.fragment_trending),
    ArticleAdapter.ArticleAdapterListener {

    private var _trendingFragmentBinding: FragmentTrendingBinding? = null
    val trendingFragmentBinding
        get() = _trendingFragmentBinding!!

    private val newsViewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _trendingFragmentBinding = FragmentTrendingBinding.inflate(inflater, container, false)
        return trendingFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupUI()
    }

    private fun setupObservers() {

        newsViewModel.topNewsCache.observe(viewLifecycleOwner, Observer {

        })

        newsViewModel.topNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    trendingFragmentBinding.progress.visibility = View.VISIBLE
                    trendingFragmentBinding.swipeRefresh.isRefreshing = false
                }
                is Resource.Error -> {
                    trendingFragmentBinding.progress.visibility = View.GONE
                    trendingFragmentBinding.swipeRefresh.isRefreshing = false
                }
                is Resource.Success -> {
                    trendingFragmentBinding.progress.visibility = View.GONE
                    trendingFragmentBinding.swipeRefresh.isRefreshing = false
                    response.data?.let { newsViewModel.updateCache(it.articles) }
                }
            }
        })
    }

    private fun setupUI() {
        trendingFragmentBinding.apply {
            lifecycleOwner = this@TrendingFragment
            viewModelNews = newsViewModel
            rvTrendingNews.adapter =
                ArticleAdapter(listener = this@TrendingFragment, isSaved = false)

            swipeRefresh.setOnRefreshListener {
                newsViewModel.getTopNewsFromServer()
                swipeRefresh.isRefreshing = true
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _trendingFragmentBinding = null
    }

    override fun onArticleClick(cardView: View, article: Article) {
        newsViewModel.selectArticle(article)
        findNavController().navigate(R.id.action_trendingFragment_to_articleFragment)
    }
}