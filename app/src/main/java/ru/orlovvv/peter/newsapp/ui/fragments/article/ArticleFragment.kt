package ru.orlovvv.peter.newsapp.ui.fragments.article

import android.R.menu
import android.content.Intent
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.databinding.FragmentArticleBinding
import ru.orlovvv.peter.newsapp.ui.NewsViewModel
import timber.log.Timber


class ArticleFragment : Fragment(R.layout.fragment_article) {

    private val newsViewModel: NewsViewModel by activityViewModels()
    private var _binding: FragmentArticleBinding? = null
    val binding
        get() = _binding!!
    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentArticleBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel.articleIsExist.observe(viewLifecycleOwner, Observer {
            Timber.d("${newsViewModel.articleIsExist.value}")
            binding.toolbar.menu.findItem(R.id.deleteArticle).isVisible = it
            binding.toolbar.menu.findItem(R.id.saveArticle).isVisible = !it
        })

        newsViewModel.isExist(args.article.url)

        binding.apply {
            viewModel = newsViewModel
            article = args.article
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            ivArticleImage.transitionName = "image_trans_${args.article.url}"
            tvArticleTitle.transitionName = "title_trans_${args.article.url}"
            tvArticleSource.transitionName = "source_trans_${args.article.url}"
            tvArticleDate.transitionName = "date_trans_${args.article.url}"

            fabSource.setOnClickListener {
                val bundle = Bundle().apply {
                    putSerializable("article", article)
                }
                findNavController().navigate(
                    R.id.action_articleInfoFragment_to_webSourceFragment,
                    bundle,
                    null,
                    null
                )
            }
        }

        setToolbarActions()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeTransition()
    }

    private fun setToolbarActions() {
        binding.toolbar.apply {
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.saveArticle -> {
                        newsViewModel.saveToReadLater(args.article)
                        true
                    }
                    R.id.shareArticle -> {
                        val intent = Intent(Intent.ACTION_SEND).setType("text/plain")
                            .putExtra(Intent.EXTRA_TEXT, args.article.url)
                        startActivity(intent)
                        true
                    }
                    R.id.deleteArticle -> {
                        newsViewModel.deleteFromReadLater(args.article)
                        findNavController().navigateUp()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun makeTransition() {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
                .addListener(object : Transition.TransitionListener {
                    override fun onTransitionStart(transition: Transition?) {
                        binding.fabSource.visibility = View.GONE
                        binding.toolbar.visibility = View.GONE
                    }

                    override fun onTransitionEnd(transition: Transition?) {
                        binding.fabSource.visibility = View.VISIBLE
                        binding.toolbar.visibility = View.VISIBLE
                    }

                    override fun onTransitionCancel(transition: Transition?) {}
                    override fun onTransitionPause(transition: Transition?) {}
                    override fun onTransitionResume(transition: Transition?) {}

                })
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}