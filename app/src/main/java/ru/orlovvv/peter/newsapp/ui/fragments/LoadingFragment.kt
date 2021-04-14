package ru.orlovvv.peter.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_news.*
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.adapters.NewsAdapter
import ru.orlovvv.peter.newsapp.databinding.FragmentLoadingBinding
import ru.orlovvv.peter.newsapp.databinding.FragmentSearchNewsBinding
import ru.orlovvv.peter.newsapp.ui.NewsActivity
import ru.orlovvv.peter.newsapp.ui.NewsViewModel

class LoadingFragment : Fragment(R.layout.fragment_loading) {

    private lateinit var newsViewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoadingBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel

        binding.apply {
            lifecycleOwner = this@LoadingFragment
        }

        newsViewModel.getTopNews()
        newsViewModel.topNewsArticlesList.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) findNavController().navigate(R.id.action_loadingFragment_to_newsFeedFragment)
        })

    }
}