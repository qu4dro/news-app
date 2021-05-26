package ru.orlovvv.peter.newsapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_news.*
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.adapters.NewsAdapter
import ru.orlovvv.peter.newsapp.databinding.FragmentNewsReadLaterBinding
import ru.orlovvv.peter.newsapp.databinding.FragmentNewsReadLaterBindingImpl
import ru.orlovvv.peter.newsapp.models.news.Article
import ru.orlovvv.peter.newsapp.ui.NewsActivity
import ru.orlovvv.peter.newsapp.ui.NewsViewModel

class NewsReadLaterFragment : Fragment(R.layout.fragment_news_read_later) {

    private lateinit var newsFeedAdapter: NewsAdapter
    private val newsViewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: FragmentNewsReadLaterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsReadLaterBinding.inflate(inflater)

        newsFeedAdapter = NewsAdapter(newsViewModel)


        binding.apply {
            lifecycleOwner = this@NewsReadLaterFragment
            viewModel = newsViewModel
            rvNewsFeed.adapter = newsFeedAdapter
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsFeedAdapter.apply {
            setOnSaveClickListener { article ->
                newsViewModel.deleteFromReadLater(article)
                Snackbar.make(view, "Article deleted", Snackbar.LENGTH_LONG)
                showSnackBar(article, view)
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
                    R.id.action_newsReadLaterFragment_to_articleInfoFragment,
                    bundle
                )
            }
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
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
                showSnackBar(article, view)
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvNewsFeed)
        }
    }

    fun showSnackBar(article: Article, view: View) {
        Snackbar.make(view, "Article deleted", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                newsViewModel.saveToReadLater(article)
            }
            .setAnchorView((activity as NewsActivity).bn_menu)
            .show()
    }
}


