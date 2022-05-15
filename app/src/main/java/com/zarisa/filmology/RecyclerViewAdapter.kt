package com.zarisa.filmology

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zarisa.filmology.databinding.MovieListItemBinding
import com.zarisa.filmology.network.Film

class RecyclerViewAdapter :
    ListAdapter<Film, RecyclerViewAdapter.MarsPhotosViewHolder>(DiffCallback) {
    class MarsPhotosViewHolder(
        private var binding: MovieListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(marsPhoto: Film) {
            binding.film = marsPhoto
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarsPhotosViewHolder {
        return MarsPhotosViewHolder(
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: MarsPhotosViewHolder, position: Int) {
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