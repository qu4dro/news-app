package ru.orlovvv.peter.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_news_read_later.*
import kotlinx.android.synthetic.main.item_article.*
import kotlinx.android.synthetic.main.item_article.view.*
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.adapters.NewsAdapter
import ru.orlovvv.peter.newsapp.databinding.FragmentNewsFeedBinding
import ru.orlovvv.peter.newsapp.models.news.Article
import ru.orlovvv.peter.newsapp.repository.NewsRepository
import ru.orlovvv.peter.newsapp.ui.NewsActivity
import ru.orlovvv.peter.newsapp.ui.NewsViewModel
import ru.orlovvv.peter.newsapp.ui.NewsViewModelProviderFactory
import ru.orlovvv.peter.newsapp.util.Constants.Companion.PAGE_SIZE
import ru.orlovvv.peter.newsapp.util.Pagination
import ru.orlovvv.peter.newsapp.util.Resource

class NewsFeedFragment : Fragment(R.layout.fragment_news_feed) {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsFeedAdapter: NewsAdapter

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        newsViewModel = (activity as NewsActivity).newsViewModel
        newsFeedAdapter = NewsAdapter(newsViewModel)
        newsFeedAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_newsFeedFragment_to_articleInfoFragment,
                bundle
            )
        }

        val binding = FragmentNewsFeedBinding.inflate(inflater)

        binding.apply {
            lifecycleOwner = this@NewsFeedFragment
            viewModel = newsViewModel
            rvNewsFeed.adapter = newsFeedAdapter
        }

        val scrollListener = Pagination(newsViewModel).scrollListener

        binding.rvNewsFeed.addOnScrollListener(scrollListener)

        return binding.root
    }
}