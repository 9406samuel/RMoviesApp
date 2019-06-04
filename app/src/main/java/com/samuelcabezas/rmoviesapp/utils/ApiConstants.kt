package com.samuelcabezas.rmoviesapp.utils

import com.samuelcabezas.rmoviesapp.BuildConfig

object ApiConstants {

    const val API_BASE_URL: String = "http://api.themoviedb.org/3/"
    const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/w200"
    const val BASE_BACKDROP_URL = "https://image.tmdb.org/t/p/w780"

    const val API_KEY: String = "api_key"
    const val API_KEY_VALUE: String = BuildConfig.TMDB_API_KEY
    const val LANGUAGE: String = "language"
    const val DEFAULT_LANGUAGE: String = "en-US"
    const val PAGE: String = "page"
    const val DEFAULT_PAGE: String = "1"

    const val POPULAR_MOVIES: String = "movie/popular"
    const val TOP_RATED_MOVIES: String = "movie/top_rated"
    const val UPCOMING_MOVIES: String = "movie/upcoming"
    const val NOW_PLAYING_MOVIES: String = "movie/now_playing"

    fun getPosterPath(posterPath: String) = BASE_POSTER_URL + posterPath

    fun getBackdropPath(backdropPath: String) = BASE_BACKDROP_URL + backdropPath
}