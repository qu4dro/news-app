package ru.orlovvv.peter.newsapp.ui.fragments.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.activity_news.*
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.databinding.FragmentSavedBinding
import ru.orlovvv.peter.newsapp.ui.fragments.feed.FeedAdapter
import ru.orlovvv.peter.newsapp.ui.NewsViewModel

class SavedFragment : Fragment(R.layout.fragment_saved) {

    private lateinit var feedFeedAdapter: FeedAdapter
    private val newsViewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: FragmentSavedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSavedBinding.inflate(inflater, container, false)

        feedFeedAdapter = FeedAdapter()


        binding.apply {
            lifecycleOwner = this@SavedFragment
            viewModel = newsViewModel
            rvNewsFeed.adapter = feedFeedAdapter
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        newsFeedAdapter.apply {
//            setOnSaveClickListener { article ->
//                newsViewModel.deleteFromReadLater(article)
//                Snackbar.make(view, "Article deleted", Snackbar.LENGTH_LONG)
//                showSnackBar(article, view)
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
//                    R.id.action_newsReadLaterFragment_to_articleInfoFragment,
//                    bundle
//                )
//            }
        }

//        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
//            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
//            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//        ) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return true
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position = viewHolder.absoluteAdapterPosition
//                val article = newsFeedAdapter.getItemWithPosition(position)
//                newsViewModel.deleteFromReadLater(article)
//                showSnackBar(article, view)
//            }
//        }
//
//        ItemTouchHelper(itemTouchHelperCallback).apply {
//            attachToRecyclerView(binding.rvNewsFeed)
//        }
//    }

//    fun showSnackBar(article: Article, view: View) {
//        Snackbar.make(view, "Article deleted", Snackbar.LENGTH_LONG)
//            .setAction("Undo") {
//                newsViewModel.saveToReadLater(article)
//            }
//            .setAnchorView((activity as NewsActivity).bn_menu)
//            .show()
//    }
}


