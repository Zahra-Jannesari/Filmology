package com.zarisa.filmology.ui.film_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zarisa.filmology.databinding.FragmentPlayVideoBinding


class PlayVideoFragment : Fragment() {
    private lateinit var binding: FragmentPlayVideoBinding
    val viewModel: DetailPageViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayVideoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.videoList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty())
                if (it[0].site == "YouTube") {
                    binding.webView.loadUrl("https://www.youtube.com/watch?v=${it[0].video_key}")
                }
        }
//        binding.webView.loadUrl("https://www.youtube.com/watch?v=lWcD2icgoGs")
    }
}