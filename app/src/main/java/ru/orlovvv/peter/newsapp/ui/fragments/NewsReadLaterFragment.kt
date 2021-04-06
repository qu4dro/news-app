package ru.orlovvv.peter.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_news.*
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.adapters.NewsAdapter
import ru.orlovvv.peter.newsapp.databinding.FragmentNewsFeedBinding
import ru.orlovvv.peter.newsapp.databinding.FragmentNewsReadLaterBinding
import ru.orlovvv.peter.newsapp.ui.NewsActivity
import ru.orlovvv.peter.newsapp.ui.NewsViewModel

class NewsReadLaterFragment : Fragment(R.layout.fragment_news_read_later) {

    private lateinit var newsFeedAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentNewsReadLaterBinding.inflate(inflater)
        newsViewModel = (activity as NewsActivity).newsViewModel

        newsFeedAdapter = NewsAdapter(newsViewModel)
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback (
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val article = newsFeedAdapter.getItemWithPosition(position)
                newsViewModel.deleteFromReadLater(article)

            }

        }
            
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvNewsFeed)
        }
        newsFeedAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_newsReadLaterFragment_to_articleInfoFragment,
                bundle
            )
        }

        binding.apply {
            lifecycleOwner = this@NewsReadLaterFragment
            viewModel = newsViewModel
            rvNewsFeed.adapter = newsFeedAdapter
        }

        return binding.root
    }


}


