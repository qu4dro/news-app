package ru.orlovvv.newsapp.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.newsapp.R
import ru.orlovvv.newsapp.adapters.ArticleAdapter
import ru.orlovvv.newsapp.data.model.Article
import ru.orlovvv.newsapp.databinding.FragmentSavedBinding
import ru.orlovvv.newsapp.viewmodels.NewsViewModel

@AndroidEntryPoint
class SavedFragment : Fragment(R.layout.fragment_saved), ArticleAdapter.ArticleAdapterListener {

    private var _savedFragmentBinding: FragmentSavedBinding? = null
    val savedFragmentBinding
        get() = _savedFragmentBinding!!

    private val newsViewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _savedFragmentBinding = FragmentSavedBinding.inflate(inflater, container, false)
        return savedFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        savedFragmentBinding.apply {
            viewModelNews = newsViewModel
            lifecycleOwner = this@SavedFragment
            rvBookmarkedArticles.adapter = ArticleAdapter(listener = this@SavedFragment, isSaved  = true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _savedFragmentBinding = null
    }

    override fun onArticleClick(cardView: View, article: Article) {
        newsViewModel.selectArticle(article)
        findNavController().navigate(R.id.action_savedFragment_to_articleFragment)
    }

}