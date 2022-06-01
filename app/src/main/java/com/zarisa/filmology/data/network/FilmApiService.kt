package com.zarisa.filmology.data.network

import com.zarisa.filmology.data.network.NetworkParams.Companion.API_KEY
import com.zarisa.filmology.model.Film
import com.zarisa.filmology.model.Films
import com.zarisa.filmology.model.UpcomingFilms
import com.zarisa.filmology.model.VideoList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): Films

    @GET("search/movie")
    suspend fun getSearchedMovie(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") searched: String
    ): Films

    @GET("discover/movie")
    suspend fun discoverMovieByGenres(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("with_genres") genres: String
    ): Films

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): UpcomingFilms

    @GET("movie/{movie_id}")
    suspend fun getFilmDetails(
        @Path("movie_id") Id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Film

    @GET("movie/{movie_id}/videos")
    suspend fun getFilmVideos(
        @Path("movie_id") Id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): VideoList
}