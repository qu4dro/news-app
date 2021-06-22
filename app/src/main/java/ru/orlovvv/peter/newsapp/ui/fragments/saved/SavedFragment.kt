package ru.orlovvv.peter.newsapp.ui.fragments.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_news.*
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.databinding.FragmentSavedBinding
import ru.orlovvv.peter.newsapp.ui.NewsViewModel
import ru.orlovvv.peter.newsapp.util.setItemsSpacing

class SavedFragment : Fragment(R.layout.fragment_saved) {

    private var savedAdapter = SavedAdapter()
    private val newsViewModel: NewsViewModel by activityViewModels()
    private var _binding: FragmentSavedBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSavedBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = this@SavedFragment
            viewModel = newsViewModel
            rvNewsFeed.adapter = savedAdapter
            rvNewsFeed.apply {
                setItemsSpacing()
                postponeEnterTransition()
                viewTreeObserver
                    .addOnPreDrawListener {
                        startPostponedEnterTransition()
                        true
                    }
            }
        }

        savedAdapter.setOnItemClickListener { article, extras ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(
                R.id.action_savedFragment_to_articleFragment,
                bundle,
                null,
                extras
            )
        }

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


