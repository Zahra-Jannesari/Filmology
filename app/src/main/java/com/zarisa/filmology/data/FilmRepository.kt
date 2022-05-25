package com.zarisa.filmology.data

import android.content.Context
import com.zarisa.filmology.data.database.AppDatabase
import com.zarisa.filmology.data.database.FilmDao
import com.zarisa.filmology.data.network.FilmApi
import com.zarisa.filmology.model.Film

var isInternetConnected = true

object FilmRepository {
    private lateinit var filmDao: FilmDao
    fun initDB(context: Context) {
        filmDao = AppDatabase.getAppDataBase(context).filmDao()
    }

    suspend fun getPopularFilms(pageNumber: Int): List<Film> {
        try {
            FilmApi.retrofitService.getPopularMovies(page = pageNumber).filmList.let {
                isInternetConnected = true
                for (i in it)
                    filmDao.insertPopularList(i)
                return it
            }
        } catch (e: Exception) {
            isInternetConnected = false
            return if (filmDao.popularListSize() > 0 && pageNumber == 1)
                filmDao.getPopularFilms()
            else
                arrayListOf()
        }
    }
}