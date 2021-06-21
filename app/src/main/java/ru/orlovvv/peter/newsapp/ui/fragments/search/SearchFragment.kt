package ru.orlovvv.peter.newsapp.ui.fragments.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.databinding.FragmentSearchBinding
import ru.orlovvv.peter.newsapp.ui.fragments.feed.FeedAdapter
import ru.orlovvv.peter.newsapp.ui.NewsViewModel

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val newsViewModel: NewsViewModel by activityViewModels()
    private var job: Job? = null
    private lateinit var binding: FragmentSearchBinding
    private lateinit var feedFeedAdapter: FeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)

        feedFeedAdapter = FeedAdapter()

        binding.apply {
            lifecycleOwner = this@SearchFragment
            viewModel = newsViewModel
            rvSearchedNews.adapter = feedFeedAdapter

            etSearchNews.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                    findNews(s)

                override fun afterTextChanged(s: Editable?) {
//
                }
            })
        }

//        val scrollListener = Pagination(
//            newsViewModel, newsViewModel.searchedNewsArticlesList
//        ) {
//            newsViewModel.findNews(
//                binding.etSearchNews.text.toString(),
//                newsViewModel.checkedSources
//            )
//        }.scrollListener
//
//        binding.rvSearchedNews.addOnScrollListener(scrollListener)


        return binding.root
    }


    private fun findNews(s: CharSequence?) {
        newsViewModel.resetArticlesList()
        job?.cancel()
        job = MainScope().launch {
            delay(500L)
            if (s != null) {
                newsViewModel.findNews(s.toString(), newsViewModel.checkedSources)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        newsFeedAdapter.apply {
//            setOnSaveClickListener { article ->
//                newsViewModel.saveToReadLater(article)
//                Snackbar.make(view, "Article saved", Snackbar.LENGTH_LONG)
//                    .setAction("Undo") {
//                        newsViewModel.saveToReadLater(article)
//                    }
//                    .setAnchorView((activity as NewsActivity).bn_menu)
//                    .show()
//            }
//
//            setOnShareClickListener {
//                val intent = Intent(Intent.ACTION_SEND).setType("text/plain")
//                    .putExtra(Intent.EXTRA_TEXT, it)
//                startActivity(intent)
//            }
//
//            setOnSourceClickListener {
//                val bundle = Bundle().apply {
//                    putSerializable("article", it)
//                }
//                findNavController().navigate(
//                    R.id.action_searchNewsFragment_to_articleInfoFragment,
//                    bundle
//                )
//            }
//        }
    }
}