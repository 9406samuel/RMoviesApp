package com.samuelcabezas.rmoviesapp.api

import com.samuelcabezas.rmoviesapp.models.network.MovieResponse
import com.samuelcabezas.rmoviesapp.utils.Api
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieService {

    @GET(Api.POPULAR_MOVIES)
    fun getCurrentPopularMovies(@Query(Api.API_KEY) api_key: String = Api.API_KEY_VALUE,
                                @Query(Api.LANGUAGE) language: String = Api.DEFAULT_LANGUAGE,
                                @Query(Api.PAGE) page: String = Api.DEFAULT_PAGE): Observable<MovieResponse>

    @GET(Api.TOP_RATED_MOVIES)
    fun getTopRatedMovies(@Query(Api.API_KEY) api_key: String = Api.API_KEY_VALUE,
                          @Query(Api.LANGUAGE) language: String = Api.DEFAULT_LANGUAGE,
                          @Query(Api.PAGE) page: String = Api.DEFAULT_PAGE): Observable<MovieResponse>

    @GET(Api.UPCOMING_MOVIES)
    fun getUpcomingMovies(@Query(Api.API_KEY) api_key: String = Api.API_KEY_VALUE,
                          @Query(Api.LANGUAGE) language: String = Api.DEFAULT_LANGUAGE,
                          @Query(Api.PAGE) page: String = Api.DEFAULT_PAGE): Observable<MovieResponse>

    @GET(Api.NOW_PLAYING_MOVIES)
    fun discoverMovies(@Query(Api.API_KEY) api_key: String = Api.API_KEY_VALUE,
                       @Query(Api.LANGUAGE) language: String = Api.DEFAULT_LANGUAGE,
                       @Query(Api.PAGE) page: String = Api.DEFAULT_PAGE): Observable<MovieResponse>

}