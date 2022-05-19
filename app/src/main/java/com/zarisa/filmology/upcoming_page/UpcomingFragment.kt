package com.zarisa.filmology.upcoming_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.zarisa.filmology.RecyclerViewAdapter
import com.zarisa.filmology.databinding.FragmentUpcomingBinding


class UpcomingFragment : Fragment() {
    private var filmPage = 1
    lateinit var binding: FragmentUpcomingBinding
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
        primarySetup()

    }

    private fun primarySetup() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerViewFilmList.adapter = RecyclerViewAdapter()
        setupBasicList()

    }

    private fun setupBasicList() {
        filmPage = 1
        viewModel.getUpcomingFilms(filmPage)
        attachMoviesOnScrollListener()
    }

    private fun attachMoviesOnScrollListener() {
        binding.recyclerViewFilmList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                    filmPage++
                    viewModel.getUpcomingFilms(filmPage)
                }
            }
        })
    }
}