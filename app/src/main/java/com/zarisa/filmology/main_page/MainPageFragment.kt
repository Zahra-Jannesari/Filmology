package com.zarisa.filmology.main_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.zarisa.filmology.R
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
        setupBasicList()
        setUpSearchPart()
        setUpFilterPart()
    }

    private fun setUpSearchPart() {
        binding.editTextSearch.doOnTextChanged { inputText, _, _, _ ->
            if (inputText.isNullOrBlank()) {
                setupBasicList()
            } else {
                viewModel.getSearchedFilm(inputText.toString())
            }
        }
    }

    private fun setUpFilterPart() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.filters,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerFilter.adapter = adapter
        }
        binding.spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p0?.getItemAtPosition(p2).toString()) {
                    "drama" -> viewModel.discoverByGenre("18")
                    "science fiction" -> viewModel.discoverByGenre("878")
                    "comedy" -> viewModel.discoverByGenre("35")
                    else -> {
                        setupBasicList()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun setupBasicList() {
        filmPage = 1
        viewModel.getFilms(filmPage)
        attachMoviesOnScrollListener()
    }

    private fun attachMoviesOnScrollListener() {
        binding.recyclerViewFilmList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                    filmPage++
                    viewModel.getFilms(filmPage)
                }
            }
        })
    }
}