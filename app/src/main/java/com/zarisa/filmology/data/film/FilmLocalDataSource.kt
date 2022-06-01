package com.zarisa.filmology.data.film

import com.zarisa.filmology.data.database.FilmDao
import com.zarisa.filmology.model.Film
import com.zarisa.filmology.model.UpcomingFilm

class FilmLocalDataSource(private val filmDao: FilmDao) {
    suspend fun deleteAllPopularFilms() {
        filmDao.deleteAllPopulars(getPopularFilms())
    }

    suspend fun getPopularFilms(): List<Film> {
        return filmDao.getPopularFilms()
    }

    suspend fun insertPopular(film: Film) {
        filmDao.insertPopularList(film)
    }

    suspend fun popularListSize(): Int {
        return filmDao.popularListSize()
    }

    suspend fun getSearchedMatches(searchedText: String): List<Film> {
        return filmDao.getMatches(searchedText)
    }

    suspend fun getFilmById(filmId: Int): Film {
        return filmDao.getFilmById(filmId)
    }

    suspend fun deleteAllUpcoming() {
        filmDao.deleteAllUpcomings(filmDao.getPopularFilms())
    }

    suspend fun insertUpcomingList(film: UpcomingFilm) {
        filmDao.insertUpcomingList(film)
    }

    suspend fun upcomingListSize(): Int {
        return filmDao.upcomingListSize()
    }

    suspend fun getUpcomingFilms(): List<UpcomingFilm> {
        return filmDao.getUpcomingFilms()
    }

}