package com.zarisa.filmology.data.film

import com.zarisa.filmology.data.network.FilmApiService
import com.zarisa.filmology.model.Film
import com.zarisa.filmology.model.UpcomingFilm
import com.zarisa.filmology.model.Video

class FilmRemoteDataSource(private val filmApiService: FilmApiService) {
    suspend fun getNumberOfPopularPages(): Int {
        return filmApiService.getPopularMovies(page = 1).pages
    }

    suspend fun getPopularList(pageNumber: Int): List<Film> {
        return filmApiService.getPopularMovies(page = pageNumber).filmList
    }

    suspend fun discoverMovieByGenre(genre: String): List<Film> {
        return filmApiService.discoverMovieByGenres(genres = genre).filmList
    }

    suspend fun getSearchedMatches(searchedText: String): List<Film> {
        return filmApiService.getSearchedMovie(
            searched = searchedText
        ).filmList
    }

    suspend fun getFilmDetails(filmId: Int): Film {
        return filmApiService.getFilmDetails(Id = filmId)
    }

    suspend fun getFilmVideos(filmId: Int): List<Video> {
        return filmApiService.getFilmVideos(filmId).videos
    }

    suspend fun getUpcomingPages(): Int {
        return filmApiService.getUpcomingMovies(page = 1).pages
    }

    suspend fun getUpcomingList(pageNumber: Int): List<UpcomingFilm> {
        return filmApiService.getUpcomingMovies(page = pageNumber).filmList
    }

}