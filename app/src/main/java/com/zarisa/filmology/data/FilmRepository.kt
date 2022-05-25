package com.zarisa.filmology.data

import android.content.Context
import com.zarisa.filmology.data.database.AppDatabase
import com.zarisa.filmology.data.database.FilmDao
import com.zarisa.filmology.data.network.FilmApi
import com.zarisa.filmology.model.Film

object FilmRepository {
    private lateinit var filmDao: FilmDao
    fun initDB(context: Context) {
        filmDao = AppDatabase.getAppDataBase(context).filmDao()
    }

    suspend fun getPopularFilms(pageNumber: Int): List<Film> {
        try {
            FilmApi.retrofitService.getPopularMovies(page = pageNumber).filmList.let {
                for (i in it)
                    filmDao.insertPopularList(i)
                return it
            }
        } catch (e: Exception) {
            if (filmDao.popularListSize() > 0)
                return filmDao.getPopularFilms()
            else throw Exception()
        }
    }
}