package ru.orlovvv.newsapp.ui.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.newsapp.R
import ru.orlovvv.newsapp.adapters.ArticleAdapter
import ru.orlovvv.newsapp.data.model.Article
import ru.orlovvv.newsapp.databinding.FragmentArticleBinding
import ru.orlovvv.newsapp.viewmodels.NewsViewModel

@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.fragment_article), ArticleAdapter.ArticleAdapterListener {

    private var _articleFragmentBinding: FragmentArticleBinding? = null
    val articleFragmentBinding
        get() = _articleFragmentBinding!!

    private val newsViewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _articleFragmentBinding = FragmentArticleBinding.inflate(inflater, container, false)
        return articleFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupUI()
    }

    private fun setupObservers() {
        newsViewModel.selectedArticle.observe(viewLifecycleOwner, Observer {
            articleFragmentBinding.article = it
            changeIcon(it.isBookmarked)
        })
    }

    private fun setupUI() {
        articleFragmentBinding.apply {
            lifecycleOwner = this@ArticleFragment

            fabSource.setOnClickListener {
                article?.let {
                    val action =
                        ArticleFragmentDirections.actionArticleFragmentToSourceFragment(it.url)
                    findNavController().navigate(action)
                }
            }

            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

            toolbar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.saveArticle -> {
                        article?.let {
                            if (it.isBookmarked) {
                                it.isBookmarked = false
                                newsViewModel.deleteArticle(it)
                                changeIcon(it.isBookmarked)
                                findNavController().navigateUp()
                            } else {
                                it.isBookmarked = true
                                newsViewModel.saveArticle(it)
                                changeIcon(it.isBookmarked)
                            }
                        }
                        true
                    }
                    R.id.shareArticle -> {
                        val intent = Intent(Intent.ACTION_SEND).setType("text/plain")
                            .putExtra(Intent.EXTRA_TEXT, article?.url!!)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun changeIcon(isBookmarked: Boolean) {
        if (isBookmarked) {
            articleFragmentBinding.toolbar.menu.findItem(R.id.saveArticle)
                .setIcon(R.drawable.ic_bookmark_remove)
        } else {
            articleFragmentBinding.toolbar.menu.findItem(R.id.saveArticle)
                .setIcon(R.drawable.ic_bookmark)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _articleFragmentBinding = null
    }

    override fun onArticleClick(cardView: View, article: Article) {

    }
}