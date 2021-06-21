package ru.orlovvv.peter.newsapp.ui.fragments.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.fragment_news_read_later.*
import kotlinx.android.synthetic.main.item_article.*
import kotlinx.android.synthetic.main.item_article.view.*
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.databinding.FragmentNewsFeedBinding
import ru.orlovvv.peter.newsapp.ui.NewsViewModel
import timber.log.Timber


class NewsFeedFragment : Fragment(R.layout.fragment_news_feed) {

    private val newsViewModel: NewsViewModel by activityViewModels()
    private val newsFeedAdapter = NewsAdapter()
    private var _binding: FragmentNewsFeedBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewsFeedBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = this@NewsFeedFragment
            viewModel = newsViewModel
            rvNewsFeed.adapter = NewsAdapter()

        }

        newsFeedAdapter.setOnClickListener {
            Timber.d(it.toString())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}