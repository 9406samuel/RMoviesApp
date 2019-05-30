package com.samuelcabezas.rmoviesapp.view.ui.main.section

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.samuelcabezas.rmoviesapp.models.entity.Movie

class MovieViewModel : ViewModel() {

    private val movieTitle = MutableLiveData<String>()
    private val moviePoster = MutableLiveData<String>()
    private val movieId = MutableLiveData<String>()
    private val movieReleaseDate = MutableLiveData<String>()

    fun bind(movie: Movie){
        movieTitle.value = movie.title
        moviePoster.value = movie.poster_path
        movieId.value = movie.id.toString()
        movieReleaseDate.value = movie.release_date
    }

    fun getMovieTitle():MutableLiveData<String>{
        return movieTitle
    }

    fun getMoviePoster():MutableLiveData<String>{
        return moviePoster
    }

    fun getMovieId():MutableLiveData<String>{
        return movieId
    }

    fun getMovieReleaseDate():MutableLiveData<String>{
        return movieReleaseDate
    }
}