package com.zarisa.filmology.network

import com.squareup.moshi.Json

data class Film(
    val id: String,
    @Json(name = "original_title") val filmName: String,
    @Json(name = "poster_path") val imgSrcUrl: String
)