package com.zarisa.filmology.domain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zarisa.filmology.databinding.MovieListItemBinding
import com.zarisa.filmology.model.UpcomingFilm

class UpcomingAdapter : ListAdapter<UpcomingFilm, UpcomingAdapter.ViewHolder>(DiffCallback) {
    inner class ViewHolder(
        private var binding: MovieListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(film: UpcomingFilm) {
            binding.filmName = film.filmName
            binding.filmImageSrc=film.imgSrcUrl
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFilm = getItem(position)
        holder.bind(currentFilm)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<UpcomingFilm>() {
        override fun areItemsTheSame(oldItem: UpcomingFilm, newItem: UpcomingFilm): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UpcomingFilm, newItem: UpcomingFilm): Boolean {
            return oldItem.id == newItem.id
        }
    }
}