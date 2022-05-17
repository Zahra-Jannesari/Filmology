package com.zarisa.filmology.main_page

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zarisa.filmology.network.Film
import com.zarisa.filmology.network.FilmApi
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE, NOT_FOUND }
class MainPageViewModel(app: Application) : AndroidViewModel(app) {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _films = MutableLiveData<List<Film>>()
    val films: LiveData<List<Film>> = _films

    fun getFilms(pageNumber: Int) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _films.value =
                    if (pageNumber == 1) FilmApi.retrofitService.getPopularMovies(page = pageNumber).filmList else _films.value?.plus(
                        FilmApi.retrofitService.getPopularMovies(page = pageNumber).filmList
                    )
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _films.value = listOf()
            }
        }
    }

    fun getSearchedFilm(pageNumber: Int, searchedText: String) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _films.value = if (pageNumber == 1) FilmApi.retrofitService.getSearchedMovie(
//                    page = pageNumber,
                    searched = searchedText
                ).filmList else _films.value?.plus(
                    FilmApi.retrofitService.getSearchedMovie(
//                        page = pageNumber,
                        searched = searchedText
                    ).filmList
                )
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.NOT_FOUND
                _films.value = listOf()
            }
        }
    }
}