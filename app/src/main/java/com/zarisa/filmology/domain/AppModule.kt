package com.zarisa.filmology.domain

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zarisa.filmology.data.database.AppDatabase
import com.zarisa.filmology.data.film.FilmLocalDataSource
import com.zarisa.filmology.data.film.FilmRemoteDataSource
import com.zarisa.filmology.data.film.FilmRepository
import com.zarisa.filmology.data.network.FilmApiService
import com.zarisa.filmology.data.network.NetworkParams
import com.zarisa.filmology.ui.film_detail.DetailPageViewModel
import com.zarisa.filmology.ui.main_page.MainPageViewModel
import com.zarisa.filmology.ui.upcoming_page.UpcomingPageViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    single {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(NetworkParams.BASE_URL)
            .client(client)
            .build()
        retrofit
    }
    single {
        val retrofit = get() as Retrofit
        val filmApiService = retrofit.create(FilmApiService::class.java)
        filmApiService
    }
    single {
        val filmDao = AppDatabase.getAppDataBase(get()).filmDao()
        filmDao
    }
    single {
        FilmLocalDataSource(get())
    }
    single {
        FilmRemoteDataSource(get())
    }
    single {
        FilmRepository(get(), get())
    }
    viewModel { DetailPageViewModel(get()) }
    viewModel { MainPageViewModel(get()) }
    viewModel { UpcomingPageViewModel(get()) }
}