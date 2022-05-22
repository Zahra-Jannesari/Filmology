package com.zarisa.filmology.network

import com.squareup.moshi.Json

data class Film(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val filmName: String,
    @Json(name = "poster_path") val imgSrcUrl: String,
    @Json(name = "backdrop_path") val backgroundImgUrl: String,
    @Json(name = "overview") val overview: String
)
data class Films(
    @Json(name = "results") val filmList: List<Film>,
    @Json(name = "page") val page: Int,
    @Json(name = "total_pages") val pages: Int
)