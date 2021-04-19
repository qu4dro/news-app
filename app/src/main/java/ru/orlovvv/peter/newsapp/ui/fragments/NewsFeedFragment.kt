package ru.orlovvv.peter.newsapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_news.*
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
    private lateinit var binding: FragmentNewsFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        newsViewModel = (activity as NewsActivity).newsViewModel
        newsFeedAdapter = NewsAdapter(newsViewModel)

        binding = FragmentNewsFeedBinding.inflate(inflater)

        binding.apply {
            lifecycleOwner = this@NewsFeedFragment
            viewModel = newsViewModel
            rvNewsFeed.adapter = newsFeedAdapter
        }

        val scrollListener = Pagination(
            newsViewModel, newsViewModel.topNewsArticlesList.value?.data?.articles!!
        ) { newsViewModel.getTopNews() }.scrollListener

        binding.rvNewsFeed.addOnScrollListener(scrollListener)

        newsViewModel.topNewsArticlesList.observe(viewLifecycleOwner, {
            val snackbar = Snackbar
                .make(
                    (activity as NewsActivity).findViewById(android.R.id.content),
                    "Check internet or API limit",
                    Snackbar.LENGTH_INDEFINITE
                ).setAnchorView((activity as NewsActivity).bn_menu)
            snackbar.setAction("Retry") {
                newsViewModel.getTopNews()
                snackbar.dismiss()
            }
            if (it.data?.articles?.isEmpty() == true) {
                snackbar.show()
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsFeedAdapter.apply {
            setOnSaveClickListener { article ->
                newsViewModel.saveToReadLater(article)
                Snackbar.make(view, "Article saved", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        newsViewModel.deleteFromReadLater(article)
                    }
                    .setAnchorView((activity as NewsActivity).bn_menu)
                    .show()
            }

            setOnShareClickListener {
                val intent = Intent(Intent.ACTION_SEND).setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT, it)
                startActivity(intent)
            }

            setOnSourceClickListener {
                val bundle = Bundle().apply {
                    putSerializable("article", it)
                }
                findNavController().navigate(
                    R.id.action_newsFeedFragment_to_articleInfoFragment,
                    bundle
                )
            }
        }
    }
}