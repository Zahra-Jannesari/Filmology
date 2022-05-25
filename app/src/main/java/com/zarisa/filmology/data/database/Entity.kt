package com.zarisa.filmology.data.database

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity
data class UpcomingFilm(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "title") val filmName: String = "",
    @Json(name = "poster_path") val imgSrcUrl: String = ""
)