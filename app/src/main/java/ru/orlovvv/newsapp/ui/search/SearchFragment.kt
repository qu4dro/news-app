package ru.orlovvv.newsapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.orlovvv.newsapp.R
import ru.orlovvv.newsapp.adapters.ArticleAdapter
import ru.orlovvv.newsapp.data.model.Article
import ru.orlovvv.newsapp.databinding.FragmentSearchBinding
import ru.orlovvv.newsapp.utils.Resource
import ru.orlovvv.newsapp.viewmodels.NewsViewModel
import timber.log.Timber

class SearchFragment : Fragment(R.layout.fragment_search), ArticleAdapter.ArticleAdapterListener {

    private var _searchFragmentBinding: FragmentSearchBinding? = null
    val searchFragmentBinding
        get() = _searchFragmentBinding!!
    private val newsViewModel: NewsViewModel by activityViewModels()
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _searchFragmentBinding = FragmentSearchBinding.inflate(inflater, container, false)
        return searchFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupObservers() {

        newsViewModel.searchedNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    searchFragmentBinding.progress.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    Timber.d(response.message)
                    searchFragmentBinding.progress.visibility = View.GONE
                }
                is Resource.Success -> {
                    searchFragmentBinding.progress.visibility = View.GONE
                    Timber.d(response.message)
                }
            }
        })
    }

    private fun setupUI() {
        searchFragmentBinding.apply {
            lifecycleOwner = this@SearchFragment
            viewModelNews = newsViewModel
            rvSearchArticle.adapter =
                ArticleAdapter(isSaved = false, listener = this@SearchFragment)
            etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(textView: TextView?, actionId: Int, keyEvent: KeyEvent?): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        newsViewModel.searchNewsFromServer(textView?.text.toString())
                        return true;
                    }
                    return false;
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _searchFragmentBinding = null
    }

    override fun onArticleClick(cardView: View, article: Article) {
        newsViewModel.selectedArticle.value = article
        findNavController().navigate(R.id.action_searchFragment_to_articleFragment)
    }

}