package com.zarisa.filmology.upcoming_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.zarisa.filmology.R
import com.zarisa.filmology.RecyclerViewAdapter
import com.zarisa.filmology.databinding.FragmentUpcomingBinding
import com.zarisa.filmology.main_page.ApiStatus


class UpcomingFragment : Fragment() {
    private var filmPage = 1
    private lateinit var binding: FragmentUpcomingBinding
    private val viewModel: UpcomingPageViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Upcoming Movies"
        primarySetup()
    }

    private fun primarySetup() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerViewFilmList.adapter = RecyclerViewAdapter()
        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                ApiStatus.LOADING -> {
                    setStateNotError(ApiStatus.LOADING)
                }
                ApiStatus.DONE -> {
                    setStateNotError(ApiStatus.DONE)
                }
                ApiStatus.ERROR -> {
                    binding.imageViewUpcoming.visibility = View.GONE
                    binding.tvNotingToShow.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
        setupBasicList()

    }

    private fun setStateNotError(state: ApiStatus) {
        binding.imageViewUpcoming.let { imageView ->
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(if (state == ApiStatus.LOADING) R.drawable.big_loading_animation else R.drawable.upcoming)
        }
        binding.tvNotingToShow.visibility = View.GONE
    }

    private fun setupBasicList() {
        filmPage = 1
        viewModel.getUpcomingFilms(filmPage)
        attachMoviesOnScrollListener()
    }

    private fun attachMoviesOnScrollListener() {
        binding.recyclerViewFilmList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                    filmPage++
                    viewModel.getUpcomingFilms(filmPage)
                }
            }
        })
    }
}