package ru.orlovvv.newsapp.ui.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.newsapp.R
import ru.orlovvv.newsapp.databinding.FragmentTrendingBinding
import ru.orlovvv.newsapp.utils.Resource
import ru.orlovvv.newsapp.viewmodels.TrendingNewsViewModel
import timber.log.Timber

@AndroidEntryPoint
class TrendingFragment : Fragment(R.layout.fragment_trending) {

    private var _trendingFragmentBinding: FragmentTrendingBinding? = null
    val trendingFragmentBinding
        get() = _trendingFragmentBinding!!

    private val trendingNewsViewModel: TrendingNewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _trendingFragmentBinding = FragmentTrendingBinding.inflate(inflater, container, false)
        return trendingFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupUI()
    }

    private fun setupObservers() {
        trendingNewsViewModel.trendingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {

                }
                is Resource.Error -> {

                }
                is Resource.Success -> {

                    response.data?.let {
                        Timber.d(it.toString())
                    }
                }
            }
        })
    }

    private fun setupUI() {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _trendingFragmentBinding = null
    }
}