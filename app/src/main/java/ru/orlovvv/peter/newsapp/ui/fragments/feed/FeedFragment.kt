package ru.orlovvv.peter.newsapp.ui.fragments.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.item_article.*
import kotlinx.android.synthetic.main.item_article.view.*
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.databinding.FragmentFeedBinding
import ru.orlovvv.peter.newsapp.ui.NewsViewModel
import timber.log.Timber


class FeedFragment : Fragment(R.layout.fragment_feed) {

    private val newsViewModel: NewsViewModel by activityViewModels()
    private val newsFeedAdapter = FeedAdapter()
    private var _binding: FragmentFeedBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFeedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = this@FeedFragment
            viewModel = newsViewModel
            rvNewsFeed.adapter = newsFeedAdapter

        }

        newsFeedAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_feedFragment_to_articleFragment, bundle)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}