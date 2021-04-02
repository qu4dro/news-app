package ru.orlovvv.peter.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.adapters.NewsAdapter
import ru.orlovvv.peter.newsapp.databinding.FragmentNewsFeedBinding
import ru.orlovvv.peter.newsapp.repository.NewsRepository
import ru.orlovvv.peter.newsapp.ui.NewsActivity
import ru.orlovvv.peter.newsapp.ui.NewsViewModel
import ru.orlovvv.peter.newsapp.ui.NewsViewModelProviderFactory
import ru.orlovvv.peter.newsapp.util.Resource

class NewsFeedFragment : Fragment(R.layout.fragment_news_feed) {

    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        newsViewModel = (activity as NewsActivity).newsViewModel

//        val feedNewsAdapter = NewsAdapter()
//        feedNewsAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW

        val binding = FragmentNewsFeedBinding.inflate(inflater)

        binding.apply {
            lifecycleOwner = this@NewsFeedFragment
            viewModel = newsViewModel
            rvNewsFeed.adapter = NewsAdapter()


        }


//        newsViewModel.topNews.observe(viewLifecycleOwner, Observer { response ->
//            when (response) {
//                is Resource.Success -> {
//                    //TODO PROGRESS HIDE
//                    newsViewModel.topNewsArticlesList.value = response.data?.articles
//
//                }
//            }
//
//        })

        return binding.root
    }
}