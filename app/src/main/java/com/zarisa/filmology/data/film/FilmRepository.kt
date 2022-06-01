package com.zarisa.filmology.data.film

import com.zarisa.filmology.model.Film
import com.zarisa.filmology.model.UpcomingFilm
import com.zarisa.filmology.model.Video

var isInternetConnected = true

class FilmRepository(
    private val filmLocalDataSource: FilmLocalDataSource,
    private val filmRemoteDataSource: FilmRemoteDataSource
) {
    suspend fun getPopularFilms(pageNumber: Int): List<Film> {
        try {
            isInternetConnected = true
            if (filmRemoteDataSource.getNumberOfPopularPages() > pageNumber) {
                filmLocalDataSource.deleteAllPopularFilms()
                filmRemoteDataSource.getPopularList(pageNumber).let {
                    for (i in it)
                        filmLocalDataSource.insertPopular(i)
                    return it
                }
            } else return arrayListOf()
        } catch (e: Exception) {
            isInternetConnected = false
            return if (filmLocalDataSource.popularListSize() > 0 && pageNumber == 1)
                filmLocalDataSource.getPopularFilms()
            else
                arrayListOf()
        }
    }

    suspend fun getFilmByGenre(genre: String): List<Film> {
        return try {
            isInternetConnected = true
            filmRemoteDataSource.discoverMovieByGenre(genre = genre)
        } catch (e: Exception) {
            isInternetConnected = false
            arrayListOf()
        }
    }

    suspend fun getMatches(searchedText: String): List<Film> {
        return try {
            isInternetConnected = true
            filmRemoteDataSource.getSearchedMatches(searchedText)
        } catch (e: java.lang.Exception) {
            isInternetConnected = false
            filmLocalDataSource.getSearchedMatches(searchedText)
        }
    }

    suspend fun getFilmBasicDetails(filmId: Int): Film {
        return try {
            isInternetConnected = true
            filmRemoteDataSource.getFilmDetails(filmId)
        } catch (e: Exception) {
            isInternetConnected = false
            filmLocalDataSource.getFilmById(filmId)
        }
    }

    suspend fun getFilmVideos(filmId: Int): List<Video> {
        return try {
            isInternetConnected = true
            filmRemoteDataSource.getFilmVideos(filmId)
        } catch (e: Exception) {
            isInternetConnected = false
            arrayListOf()
        }
    }

    suspend fun getUpcomingFilms(pageNumber: Int): List<UpcomingFilm> {
        try {
            isInternetConnected = true
            if (filmRemoteDataSource.getUpcomingPages() > pageNumber) {
                filmLocalDataSource.deleteAllUpcoming()
                filmRemoteDataSource.getUpcomingList(pageNumber).let {
                    for (i in it)
                        filmLocalDataSource.insertUpcomingList(i)
                    return it
                }
            } else return arrayListOf()
        } catch (e: Exception) {
            isInternetConnected = false
            return if (filmLocalDataSource.upcomingListSize() > 0 && pageNumber == 1)
                filmLocalDataSource.getUpcomingFilms()
            else
                arrayListOf()
        }
    }
}