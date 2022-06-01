package com.zarisa.filmology.ui.film_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zarisa.filmology.data.film.FilmRepository
import com.zarisa.filmology.model.Film
import com.zarisa.filmology.model.Video
import com.zarisa.filmology.ui.main_page.ApiStatus
import kotlinx.coroutines.launch

class DetailPageViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _film = MutableLiveData<Film>()
    val film: LiveData<Film> = _film

    val videoList = mutableListOf<Video>()

    fun getFilmDetails(filmId: Int) {
        viewModelScope.launch {
            //basic details: name, overview, imageSrc and backgroundImg
            _status.value = ApiStatus.LOADING
            _film.value = filmRepository.getFilmBasicDetails(filmId)
            _status.value = ApiStatus.DONE
            //video list
            videoList.clear()
            film.value?.let { videoList.addAll(filmRepository.getFilmVideos(filmId)) }
        }
    }
}