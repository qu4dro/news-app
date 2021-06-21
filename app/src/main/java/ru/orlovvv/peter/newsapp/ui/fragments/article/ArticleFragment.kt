package ru.orlovvv.peter.newsapp.ui.fragments.article

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.databinding.FragmentArticleBinding
import ru.orlovvv.peter.newsapp.ui.NewsViewModel


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
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}