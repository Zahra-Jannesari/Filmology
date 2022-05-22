package com.zarisa.filmology.detail_page

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zarisa.filmology.main_page.ApiStatus
import com.zarisa.filmology.network.Film
import com.zarisa.filmology.network.FilmApi
import kotlinx.coroutines.launch

class DetailPageViewModel(app: Application) : AndroidViewModel(app) {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _film = MutableLiveData<Film>()
    val film: LiveData<Film> = _film

    fun getFilmDetails(filmId: Int) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _film.value = FilmApi.retrofitService.getFilmDetails(Id=filmId)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _film.value = Film()
            }
        }
    }
}