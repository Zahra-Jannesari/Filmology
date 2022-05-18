package com.zarisa.filmology.upcoming_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zarisa.filmology.RecyclerViewAdapter
import com.zarisa.filmology.databinding.FragmentUpcomingBinding


class UpcomingFragment : Fragment() {
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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerViewFilmList.adapter = RecyclerViewAdapter()
        viewModel.getUpcomingFilms()
    }

}