package com.zarisa.filmology.ui

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zarisa.filmology.R
import com.zarisa.filmology.domain.RecyclerViewAdapter
import com.zarisa.filmology.domain.UpcomingAdapter
import com.zarisa.filmology.ui.main_page.ApiStatus
import com.zarisa.filmology.model.Film
import com.zarisa.filmology.model.UpcomingFilm

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Film>?) {
    val adapter = recyclerView.adapter as RecyclerViewAdapter
    adapter.submitList(data)
}

@BindingAdapter("upcomingListData")
fun bindUpcomingRecyclerView(recyclerView: RecyclerView, data: List<UpcomingFilm>?) {
    val adapter = recyclerView.adapter as UpcomingAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imgView)
            .load("https://image.tmdb.org/t/p/w500$imgUrl")
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .into(imgView)
    }
}

@BindingAdapter("backgroundImageUrl")
fun bindBackgroundImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imgView)
            .load("https://image.tmdb.org/t/p/w500$imgUrl")
            .into(imgView)
    }
}

@BindingAdapter("ApiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.big_loading_animation)
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.NOT_FOUND -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_baseline_not_found_24)
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {}
    }
}