package ru.orlovvv.newsapp.ui.source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.orlovvv.newsapp.R
import ru.orlovvv.newsapp.databinding.FragmentSourceBinding

class SourceFragment : Fragment(R.layout.fragment_source) {

    private var _sourceFragmentBinding: FragmentSourceBinding? = null
    val sourceFragmentBinding
        get() = _sourceFragmentBinding!!

    private val args: SourceFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _sourceFragmentBinding = FragmentSourceBinding.inflate(inflater, container, false)
        return sourceFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {

        sourceFragmentBinding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        sourceFragmentBinding.wvSource.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(args.link)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _sourceFragmentBinding = null
    }
}