package com.zarisa.filmology.model

import com.squareup.moshi.Json

data class VideoList(
    @Json(name = "results") val videos: List<Video>
)

data class Video(
    @Json(name = "key") val video_key: String,
    @Json(name = "site") val site: String
)