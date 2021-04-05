package ru.orlovvv.peter.newsapp.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.adapters.NewsAdapter
import ru.orlovvv.peter.newsapp.databinding.FragmentNewsFeedBinding
import ru.orlovvv.peter.newsapp.databinding.FragmentSearchNewsBinding
import ru.orlovvv.peter.newsapp.ui.NewsActivity
import ru.orlovvv.peter.newsapp.ui.NewsViewModel

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    private lateinit var newsViewModel: NewsViewModel
    private var job: Job? = null
    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var newsFeedAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchNewsBinding.inflate(inflater)

        newsViewModel = (activity as NewsActivity).newsViewModel

        newsFeedAdapter = NewsAdapter()
        newsFeedAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleInfoFragment,
                bundle
            )
        }

        binding.apply {
            lifecycleOwner = this@SearchNewsFragment
            viewModel = newsViewModel
            rvSearchedNews.adapter = newsFeedAdapter

            etSearchNews.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                    showSearchedNews(s)

                override fun afterTextChanged(s: Editable?) {
                    showOrHideSearchChips(s)
                    showOrHideNewsRecycler(s)
                }
            })
        }

        return binding.root
    }


    private fun showSearchedNews(s: CharSequence?) {
        job?.cancel()
        job = MainScope().launch {
            delay(500L)
            if (s != null) {
                newsViewModel.findNews(s.toString())
            }
        }
    }

    private fun showOrHideNewsRecycler(s: Editable?) {
        if (s != null) {
            if (s.isNotEmpty()) {
                binding.rvSearchedNews.visibility = View.VISIBLE
            } else {
                binding.rvSearchedNews.visibility = View.GONE
            }
        }
    }

    private fun showOrHideSearchChips(s: CharSequence?) {
        if (s != null) {
            if (s.isEmpty()) {
                binding.clCategoriesBlock.visibility = View.VISIBLE
            } else {
                binding.clCategoriesBlock.visibility = View.GONE
            }
        }
    }

}