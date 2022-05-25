package com.zarisa.filmology.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zarisa.filmology.model.Film
import com.zarisa.filmology.model.UpcomingFilm

@Dao
interface FilmDao {
    //-----------------------------for popular list----------------------------------//
    @Query("SELECT * From Film")
    fun getPopularFilms(): LiveData<List<Film>>

    @Query("SELECT * From Film Where filmName LIKE '%' || :text || '%'")
    fun getMatches(text: String): List<Film>

    @Query("SELECT * From Film Where id in (:filmId)")
    fun getFilmById(filmId: Int): Film

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularList(vararg list: List<Film>)

    //------------------------------for upcoming list---------------------------------//
    @Query("SELECT * From UpcomingFilm")
    fun getUpcomingFilms(): LiveData<List<UpcomingFilm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingList(vararg list: List<UpcomingFilm>)
}