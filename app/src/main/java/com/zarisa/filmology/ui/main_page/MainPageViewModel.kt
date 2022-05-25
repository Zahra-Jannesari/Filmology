package com.zarisa.filmology.ui.main_page

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zarisa.filmology.data.FilmRepository
import com.zarisa.filmology.model.Film
import com.zarisa.filmology.data.network.FilmApi
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE, NOT_FOUND }
class MainPageViewModel(app: Application) : AndroidViewModel(app) {
    init {
        FilmRepository.initDB(app.applicationContext)
    }
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _films = MutableLiveData<List<Film>>()
    val films: LiveData<List<Film>> = _films

    fun getFilms(pageNumber: Int) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            _films.value = listOf()
            try {
                _films.value =
                    if (pageNumber == 1) FilmRepository.getPopularFilms(pageNumber) else _films.value?.plus(
                        FilmRepository.getPopularFilms(pageNumber)
                    )
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _films.value = listOf()
            }
        }
    }

    fun getSearchedFilm(searchedText: String) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            _films.value = listOf()
            try {
                _films.value = FilmApi.retrofitService.getSearchedMovie(
                    searched = searchedText
                ).filmList
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.NOT_FOUND
                _films.value = listOf()
            }
        }
    }

    fun discoverByGenre(genres: String) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            _films.value = listOf()
            try {
                _films.value = listOf()
                _films.value =
                    _films.value?.plus(FilmApi.retrofitService.discoverMovieByGenres(genres = genres).filmList)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _films.value = listOf()
            }
        }
    }
}