package com.zarisa.filmology

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.zarisa.filmology.main_page.ApiStatus
import com.zarisa.filmology.network.Film

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Film>?) {
    val adapter = recyclerView.adapter as RecyclerViewAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

