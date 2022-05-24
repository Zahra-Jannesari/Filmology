package com.zarisa.filmology.film_detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zarisa.filmology.main_page.ApiStatus
import com.zarisa.filmology.network.Film
import com.zarisa.filmology.network.FilmApi
import com.zarisa.filmology.network.Video
import kotlinx.coroutines.launch

class DetailPageViewModel(app: Application) : AndroidViewModel(app) {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _film = MutableLiveData<Film>()
    val film: LiveData<Film> = _film

    private val _videoList = MutableLiveData<List<Video>>()
    val videoList: LiveData<List<Video>> = _videoList

    fun getFilmDetails(filmId: Int) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _film.value = FilmApi.retrofitService.getFilmDetails(Id = filmId)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _film.value = Film()
            }
        }
    }

    fun getFilmVideos() {
        viewModelScope.launch {
            try {
                _videoList.value =
                    film.value?.let { FilmApi.retrofitService.getFilmVideos(it.id).videos }

            } catch (e: Exception) {
            }
        }
    }
}