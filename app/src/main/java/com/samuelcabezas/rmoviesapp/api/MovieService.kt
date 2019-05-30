package com.samuelcabezas.rmoviesapp.api

import com.samuelcabezas.rmoviesapp.models.network.MovieResponse
import com.samuelcabezas.rmoviesapp.utils.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieService {

    @GET(Constants.CURRENT_POPULAR_MOVIES)
    fun getCurrentPopularMovies(@Query(Constants.API_KEY) api_key: String): Observable<MovieResponse>

    @GET(Constants.TOP_RATED_MOVIES)
    fun getTopRatedMovies(@Query(Constants.API_KEY) api_key: String): Observable<MovieResponse>

    @GET(Constants.UPCOMING_MOVIES)
    fun getUpcomingMovies(@Query(Constants.API_KEY) api_key: String): Observable<MovieResponse>

}