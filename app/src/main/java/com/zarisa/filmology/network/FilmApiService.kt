package com.zarisa.filmology.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
private const val API_KEY = "b8fb74a7f7ebe3f2402f6de80059d5a5"

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
    ): Films

    @GET("movie/{movie_id}")
    suspend fun getFilmDetails(
        @Path("movie_id") Id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Film
}

object FilmApi {
    val retrofitService: FilmApiService by lazy { retrofit.create(FilmApiService::class.java) }
}