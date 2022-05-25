package com.zarisa.filmology.ui.film_detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zarisa.filmology.data.FilmRepository
import com.zarisa.filmology.ui.main_page.ApiStatus
import com.zarisa.filmology.model.Film
import com.zarisa.filmology.model.Video
import kotlinx.coroutines.launch

class DetailPageViewModel(app: Application) : AndroidViewModel(app) {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _film = MutableLiveData<Film>()
    val film: LiveData<Film> = _film

    val videoList = mutableListOf<Video>()

    fun getFilmDetails(filmId: Int) {
        viewModelScope.launch {
            //basic details: name, overview, imageSrc and backgroundImg
            _status.value = ApiStatus.LOADING
            _film.value = FilmRepository.getFilmBasicDetails(filmId)
            _status.value = ApiStatus.DONE
            //video list
            videoList.clear()
            film.value?.let { videoList.addAll(FilmRepository.getFilmVideos(filmId)) }
        }
    }
}