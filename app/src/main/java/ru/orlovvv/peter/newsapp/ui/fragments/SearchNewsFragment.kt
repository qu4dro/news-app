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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        newsViewModel = (activity as NewsActivity).newsViewModel

        val binding = FragmentSearchNewsBinding.inflate(inflater)



        binding.apply {
            lifecycleOwner = this@SearchNewsFragment
            viewModel = newsViewModel
            rvSearchedNews.adapter = NewsAdapter()

            etSearchNews.addTextChangedListener(object : TextWatcher {

                var job: Job? = null

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                    chCategories.visibility = View.GONE
                    tvPopularCategories.visibility = View.GONE
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    job?.cancel()
                    job = MainScope().launch {
                        delay(500L)
                        if (s != null) {
                            if (s.isNotEmpty())
                                newsViewModel.findNews(s.toString())
                        }
                    }
                    rvSearchedNews.visibility = View.VISIBLE
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })

        }
//        newsViewModel.findNews("О")
        return binding.root
    }

}