package com.zarisa.filmology.ui.film_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zarisa.filmology.R
import com.zarisa.filmology.databinding.FragmentFilmDetailBinding
import com.zarisa.filmology.ui.main_page.ApiStatus
import com.zarisa.filmology.ui.main_page.filmID
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmDetailFragment : Fragment() {
    private val viewModel: DetailPageViewModel by viewModel()
    private lateinit var binding: FragmentFilmDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        primarySetup()
    }

    private fun primarySetup() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.getFilmDetails(requireArguments().getInt(filmID, 0))
        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                ApiStatus.LOADING -> {
                    binding.imageViewState.let { imageView ->
                        imageView.visibility = View.VISIBLE
                        imageView.setImageResource(R.drawable.big_loading_animation)
                    }
                    binding.fabPlay.visibility = View.GONE
                }
                ApiStatus.DONE -> {
                    binding.imageViewState.visibility = View.GONE
                    binding.fabPlay.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
        binding.fabPlay.setOnClickListener { imageButton ->
            imageButton.setBackgroundResource(R.drawable.loading_animation)
            viewModel.videoList.let { videoList ->
                if (videoList.isNotEmpty()) {
                    val urlBundle = bundleOf("url" to videoList[0].video_key)
                    findNavController().navigate(
                        R.id.action_filmDetailFragment_to_playVideoFragment,
                        urlBundle
                    )
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please check your connection and try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}