package ru.orlovvv.newsapp.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.orlovvv.newsapp.R
import ru.orlovvv.newsapp.databinding.FragmentSavedBinding

class SavedFragment : Fragment(R.layout.fragment_saved) {

    private var _savedFragmentBinding: FragmentSavedBinding? = null
    val savedFragmentBinding
        get() = _savedFragmentBinding!!

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _savedFragmentBinding = null
    }

}