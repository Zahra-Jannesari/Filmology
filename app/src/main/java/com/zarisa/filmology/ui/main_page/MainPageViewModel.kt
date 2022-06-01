package com.zarisa.filmology.ui.main_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zarisa.filmology.data.film.FilmRepository
import com.zarisa.filmology.data.film.isInternetConnected
import com.zarisa.filmology.model.Film
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE, NOT_FOUND }
class MainPageViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _films = MutableLiveData<List<Film>>()
    val films: LiveData<List<Film>> = _films

    fun getFilms(pageNumber: Int) {
        viewModelScope.launch {
            if (isInternetConnected) {
                _status.value = ApiStatus.LOADING
                if (pageNumber == 1) _films.value = listOf()
            }
            filmRepository.getPopularFilms(pageNumber).let {
                when {
                    it.isNotEmpty() -> {
                        _films.value = if (pageNumber == 1) it else _films.value?.plus(it)
                        _status.value = ApiStatus.DONE
                    }
                    _films.value.isNullOrEmpty() -> _status.value = ApiStatus.ERROR
                    else -> _status.value = ApiStatus.DONE
                }
            }
        }
    }

    fun getMatchesWithSearch(searchedText: String) {
        viewModelScope.launch {
            _films.value = listOf()
            _status.value = ApiStatus.LOADING
            _films.value = filmRepository.getMatches(searchedText)
            if (_films.value.isNullOrEmpty())
                _status.value = ApiStatus.NOT_FOUND
            else _status.value = ApiStatus.DONE
        }
    }

    fun discoverByGenre(genres: String) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            _films.value = filmRepository.getFilmByGenre(genres)
            if (_films.value.isNullOrEmpty())
                _status.value = ApiStatus.ERROR
            else _status.value = ApiStatus.DONE
        }
    }
}