package com.samuelcabezas.rmoviesapp.api

import com.samuelcabezas.rmoviesapp.models.network.MovieResponse
import com.samuelcabezas.rmoviesapp.utils.ApiConstants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET(ApiConstants.POPULAR_MOVIES)
    fun getCurrentPopularMovies(@Query(ApiConstants.API_KEY) api_key: String = ApiConstants.API_KEY_VALUE,
                                @Query(ApiConstants.LANGUAGE) language: String = ApiConstants.DEFAULT_LANGUAGE,
                                @Query(ApiConstants.PAGE) page: String = ApiConstants.DEFAULT_PAGE): Observable<MovieResponse>

    @GET(ApiConstants.TOP_RATED_MOVIES)
    fun getTopRatedMovies(@Query(ApiConstants.API_KEY) api_key: String = ApiConstants.API_KEY_VALUE,
                          @Query(ApiConstants.LANGUAGE) language: String = ApiConstants.DEFAULT_LANGUAGE,
                          @Query(ApiConstants.PAGE) page: String = ApiConstants.DEFAULT_PAGE): Observable<MovieResponse>

    @GET(ApiConstants.UPCOMING_MOVIES)
    fun getUpcomingMovies(@Query(ApiConstants.API_KEY) api_key: String = ApiConstants.API_KEY_VALUE,
                          @Query(ApiConstants.LANGUAGE) language: String = ApiConstants.DEFAULT_LANGUAGE,
                          @Query(ApiConstants.PAGE) page: String = ApiConstants.DEFAULT_PAGE): Observable<MovieResponse>

    @GET(ApiConstants.NOW_PLAYING_MOVIES)
    fun discoverMovies(@Query(ApiConstants.API_KEY) api_key: String = ApiConstants.API_KEY_VALUE,
                       @Query(ApiConstants.LANGUAGE) language: String = ApiConstants.DEFAULT_LANGUAGE,
                       @Query(ApiConstants.PAGE) page: String = ApiConstants.DEFAULT_PAGE): Observable<MovieResponse>

}