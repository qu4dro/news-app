package ru.orlovvv.peter.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_news_read_later.*
import kotlinx.android.synthetic.main.item_article.*
import kotlinx.android.synthetic.main.item_article.view.*
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.adapters.NewsAdapter
import ru.orlovvv.peter.newsapp.databinding.FragmentNewsFeedBinding
import ru.orlovvv.peter.newsapp.ui.NewsActivity
import ru.orlovvv.peter.newsapp.ui.NewsViewModel
import ru.orlovvv.peter.newsapp.util.Pagination

class NewsFeedFragment : Fragment(R.layout.fragment_news_feed) {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsFeedAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        newsViewModel = (activity as NewsActivity).newsViewModel
        newsFeedAdapter = NewsAdapter(newsViewModel)
        newsFeedAdapter.setOnSourceClickListener {
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

        val scrollListener = Pagination(
            newsViewModel, newsViewModel.topNewsArticlesList
        ) { newsViewModel.getTopNews() }.scrollListener

        binding.rvNewsFeed.addOnScrollListener(scrollListener)

        newsViewModel.topNewsArticlesList.observe(viewLifecycleOwner, {
            if (it == null || it.isEmpty()) {
                Snackbar.make(
                    (activity as NewsActivity).findViewById(android.R.id.content),
                    "Check internet or API limit",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Retry") {
                    newsViewModel.getTopNews()
                }
                    .show()
            }
        })

        return binding.root
    }

}