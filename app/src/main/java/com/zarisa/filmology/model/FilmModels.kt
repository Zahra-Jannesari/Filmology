package com.zarisa.filmology.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Films(
    @Json(name = "results") val filmList: List<Film>,
    @Json(name = "page") val page: Int,
    @Json(name = "total_pages") val pages: Int
)

@Entity
data class Film(
    @PrimaryKey
    @Json(name = "id") val id: Int = 0,
    @Json(name = "title") val filmName: String = "",
    @Json(name = "poster_path") val imgSrcUrl: String = "",
    @Json(name = "backdrop_path") val backgroundImgUrl: String = "",
    @Json(name = "overview") val overview: String = ""
)

data class UpcomingFilms(
    @Json(name = "results") val filmList: List<UpcomingFilm>,
    @Json(name = "page") val page: Int,
    @Json(name = "total_pages") val pages: Int
)

@Entity
data class UpcomingFilm(
    @PrimaryKey
    @Json(name = "id") val id: Int = 0,
    @Json(name = "title") val filmName: String = "",
    @Json(name = "poster_path") val imgSrcUrl: String = ""
)