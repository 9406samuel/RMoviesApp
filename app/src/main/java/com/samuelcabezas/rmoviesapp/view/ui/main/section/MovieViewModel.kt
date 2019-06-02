package com.samuelcabezas.rmoviesapp.view.ui.main.section

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.samuelcabezas.rmoviesapp.models.entity.Movie
import com.samuelcabezas.rmoviesapp.utils.Api

class MovieViewModel : ViewModel() {

    private val title = MutableLiveData<String>()
    private val releaseDate = MutableLiveData<String>()
    private val overview = MutableLiveData<String>()
    private val voteAverage = MutableLiveData<String>()
    private val originalLanguage = MutableLiveData<String>()
    private val urlPosterImage = MutableLiveData<String>()
    private val urlBackdropImage = MutableLiveData<String>()

    fun bind(movie: Movie){
        title.value = movie.title
        releaseDate.value = movie.release_date
        overview.value = movie.overview
        voteAverage.value = movie.vote_average.toString()
        originalLanguage.value = movie.original_language
        urlPosterImage.value = Api.getPosterPath(movie.poster_path)
        urlBackdropImage.value = Api.getBackdropPath(movie.backdrop_path)
    }

    fun getTitle():MutableLiveData<String>{
        return title
    }

    fun getReleaseDate():MutableLiveData<String>{
        return releaseDate
    }

    fun getOverview():MutableLiveData<String>{
        return overview
    }

    fun getVoteAverage():MutableLiveData<String>{
        return voteAverage
    }

    fun getOriginalLanguage():MutableLiveData<String>{
        return originalLanguage
    }

    fun getUrlPosterImage(): MutableLiveData<String> {
        return urlPosterImage
    }

    fun getUrlBackdropImage(): MutableLiveData<String> {
        return urlPosterImage
    }
}