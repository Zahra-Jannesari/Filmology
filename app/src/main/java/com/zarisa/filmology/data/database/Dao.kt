package com.zarisa.filmology.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zarisa.filmology.model.Film
import com.zarisa.filmology.model.UpcomingFilm

@Dao
interface FilmDao {
    //-----------------------------for popular list----------------------------------//
    @Query("SELECT * From Film")
    suspend fun getPopularFilms(): List<Film>

    @Query("SELECT * From Film Where filmName LIKE '%' || :text || '%'")
    suspend fun getMatches(text: String): List<Film>

    @Query("SELECT * From Film Where id in (:filmId)")
    suspend fun getFilmById(filmId: Int): Film

    @Query("SELECT count(*) From Film")
    suspend fun popularListSize(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularList(vararg films: Film)

    //------------------------------for upcoming list---------------------------------//
    @Query("SELECT * From UpcomingFilm")
    suspend fun getUpcomingFilms(): List<UpcomingFilm>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingList(vararg films: UpcomingFilm)
}