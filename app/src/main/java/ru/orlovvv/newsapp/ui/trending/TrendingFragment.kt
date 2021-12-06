package ru.orlovvv.newsapp.ui.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.orlovvv.newsapp.R
import ru.orlovvv.newsapp.databinding.FragmentTrendingBinding

class TrendingFragment : Fragment(R.layout.fragment_trending) {

    private var _trendingFragmentBinding: FragmentTrendingBinding? = null
    val trendingFragmentBinding
        get() = _trendingFragmentBinding!!

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _trendingFragmentBinding = null
    }
}