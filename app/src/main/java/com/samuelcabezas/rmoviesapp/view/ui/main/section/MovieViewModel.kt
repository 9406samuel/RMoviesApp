package com.samuelcabezas.rmoviesapp.view.ui.main.section

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.samuelcabezas.rmoviesapp.models.entity.Movie
import com.samuelcabezas.rmoviesapp.utils.Api
import java.text.DateFormat
import java.util.*

class MovieViewModel : ViewModel() {
    private val title = MutableLiveData<String>()
    private val originalTitle = MutableLiveData<String>()
    private val releaseDate = MutableLiveData<String>()
    private val overview = MutableLiveData<String>()
    private val voteAverage = MutableLiveData<String>()
    private val originalLanguage = MutableLiveData<String>()
    private val voteCount = MutableLiveData<String>()
    private val popularity = MutableLiveData<String>()
    private val urlPosterImage = MutableLiveData<String>()
    private val urlBackdropImage = MutableLiveData<String>()

    fun bind(movie: Movie) {
        title.value = movie.title
        originalTitle.value = movie.original_title
        releaseDate.value = movie.release_date
        overview.value = movie.overview
        voteAverage.value = movie.vote_average.toString()
        originalLanguage.value = movie.original_language
        voteCount.value = movie.vote_count.toString()
        popularity.value = movie.popularity.toString()
        urlPosterImage.value = Api.getPosterPath(movie.poster_path)
        urlBackdropImage.value = Api.getBackdropPath(movie.backdrop_path)
    }

    fun getTitle(): MutableLiveData<String> {
        return title
    }

    fun getOriginalTitle(): MutableLiveData<String> {
        originalTitle.value = "Original title: ${originalTitle.value}"
        return originalTitle
    }

    fun getReleaseDate(): MutableLiveData<String> {
        releaseDate.value = "Release date: ${releaseDate.value}"
        return releaseDate
    }

    fun getOverview(): MutableLiveData<String> {
        overview.value = "Overview: ${overview.value}"
        return overview
    }

    fun getVoteAverage(): MutableLiveData<String> {
        voteAverage.value = "Vote average: ${voteAverage.value}"
        return voteAverage
    }

    fun getOriginalLanguage(): MutableLiveData<String> {
        originalLanguage.value = "Original language: ${originalLanguage.value}"
        return originalLanguage
    }

    fun getVoteCount(): MutableLiveData<String> {
        voteCount.value = "Vote count: ${voteCount.value} votes"
        return voteCount
    }

    fun getPopularity(): MutableLiveData<String> {
        popularity.value = "Popularity: ${popularity.value}"
        return popularity
    }

    fun getUrlPosterImage(): MutableLiveData<String> {
        return urlPosterImage
    }

    fun getUrlBackdropImage(): MutableLiveData<String> {
        return urlBackdropImage
    }
}