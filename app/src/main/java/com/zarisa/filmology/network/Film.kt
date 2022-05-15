package com.zarisa.filmology.network

import com.squareup.moshi.Json

data class Film(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val filmName: String,
    @Json(name = "poster_path") val imgSrcUrl: String
)