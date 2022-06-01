package com.zarisa.filmology.ui.upcoming_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zarisa.filmology.data.film.FilmRepository
import com.zarisa.filmology.data.film.isInternetConnected
import com.zarisa.filmology.model.UpcomingFilm
import com.zarisa.filmology.ui.main_page.ApiStatus
import kotlinx.coroutines.launch

class UpcomingPageViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _films = MutableLiveData<List<UpcomingFilm>>()
    val films: LiveData<List<UpcomingFilm>> = _films

    fun getUpcomingFilms(pageNumber: Int) {
        viewModelScope.launch {
            if (isInternetConnected) {
                _status.value = ApiStatus.LOADING
                if (pageNumber == 1) _films.value = listOf()
            }
            filmRepository.getUpcomingFilms(pageNumber).let {
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
}