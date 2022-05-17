package com.zarisa.filmology.main_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.zarisa.filmology.RecyclerViewAdapter
import com.zarisa.filmology.databinding.FragmentMainPageBinding


class MainPageFragment : Fragment() {
    private var filmPage = 1
    private lateinit var binding: FragmentMainPageBinding
    private val viewModel: MainPageViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainPageBinding.inflate(inflater)
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
        viewModel.getFilms(filmPage)
        attachMoviesOnScrollListener(true)
        binding.editTextSearch.doOnTextChanged { inputText, _, _, _ ->
            filmPage = 1
            if (inputText.isNullOrBlank()) {
                viewModel.getFilms(filmPage)
                attachMoviesOnScrollListener(true)
            } else {
                viewModel.getSearchedFilm(filmPage, inputText.toString())
//                attachMoviesOnScrollListener(false, inputText.toString())
            }
        }
//        binding.textFieldSearch.setEndIconOnClickListener {
//            filmPage = 1
//            binding.editTextSearch.text.let {
//                if (it.isNullOrBlank()) {
//                    viewModel.getFilms(filmPage)
//                    attachMoviesOnScrollListener(true)
//                } else {
//                    viewModel.getSearchedFilm(filmPage, it.toString())
//                    attachMoviesOnScrollListener(false, it.toString())
//                }
//            }
//
//        }
    }

    private fun attachMoviesOnScrollListener(forTotalMovies: Boolean, searchedText: String = "") {
        binding.recyclerViewFilmList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                    filmPage++
                    if (forTotalMovies)
                        viewModel.getFilms(filmPage)
                    else viewModel.getSearchedFilm(filmPage, searchedText)
                }
            }
        })
    }


}