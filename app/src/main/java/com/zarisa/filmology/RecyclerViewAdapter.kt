package com.zarisa.filmology

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zarisa.filmology.databinding.MovieListItemBinding
import com.zarisa.filmology.network.Film
typealias showFilmDetails=(Int)->Unit
class RecyclerViewAdapter(var onFilmClick:showFilmDetails={}) :
    ListAdapter<Film, RecyclerViewAdapter.ViewHolder>(DiffCallback) {
    inner class ViewHolder(
        private var binding: MovieListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            binding.film = film
            binding.executePendingBindings()
            binding.root.setOnClickListener{onFilmClick(film.id)}
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
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.id == newItem.id
        }
    }
}