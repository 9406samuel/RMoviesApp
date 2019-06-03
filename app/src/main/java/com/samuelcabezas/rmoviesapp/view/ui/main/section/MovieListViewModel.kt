package com.samuelcabezas.rmoviesapp.view.ui.main.section

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import android.view.View
import com.samuelcabezas.rmoviesapp.R
import com.samuelcabezas.rmoviesapp.models.entity.Movie
import com.samuelcabezas.rmoviesapp.room.MovieDao
import com.samuelcabezas.rmoviesapp.api.ApiClient
import com.samuelcabezas.rmoviesapp.utils.Constants.POPULAR
import com.samuelcabezas.rmoviesapp.utils.Constants.TOP_RATED
import com.samuelcabezas.rmoviesapp.utils.Constants.UPCOMING
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MovieListViewModel(private val movieDao: MovieDao, private var category: String) : ViewModel() {

    private val tag: String = MovieListViewModel::class.java.simpleName

    val movieListAdapter: MovieListAdapter = MovieListAdapter()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val loadMoviesListFromAPI: ((String) -> Unit) = {
        category = it
        loadOnlineMoviesList()
    }

    private lateinit var onlineSubscription: Disposable
    private lateinit var offlineSubscription: Disposable

    init {
        loadOfflineMoviesList()
        loadOnlineMoviesList()
    }

    private fun loadOnlineMoviesList() {

        //loadOfflineMoviesList()

            onlineSubscription = Observable.fromCallable {
            (when (category) {
                POPULAR -> ApiClient.getApiClient().getCurrentPopularMovies()
                TOP_RATED -> ApiClient.getApiClient().getTopRatedMovies()
                UPCOMING -> ApiClient.getApiClient().getUpcomingMovies()
                else -> ApiClient.getApiClient().discoverMovies()
            })
        }.concatMap { apiResponse ->
            apiResponse.concatMap { movieResponse ->
                //movieDao.insertMovies(*(movieResponse.getMovieResults().forEach { x ->x.category = "" }).toTypedArray())
                movieResponse.getMovieResults().forEach { movie ->
                    movie.category = category
                    movieDao.insertMovie(movie)
                }
                Observable.just(movieResponse.getMovieResults())
            }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveMoviesListStart() }
                .doOnTerminate { onRetrieveMoviesListFinish() }
                .subscribe({ result -> onRetrieveMoviesListSuccess(result) },
                        { error -> onRetrieveOnlineMoviesListError(error) })
    }

    private fun loadOfflineMoviesList(){

        offlineSubscription = Observable.fromCallable {
            when (category) {
                POPULAR -> movieDao.selectMoviesByCategory(POPULAR)
                TOP_RATED -> movieDao.selectMoviesByCategory(TOP_RATED)
                UPCOMING -> movieDao.selectMoviesByCategory(UPCOMING)
                else -> movieDao.movies
            }
        }.concatMap { moviesList -> Observable.just(moviesList) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnSubscribe { onRetrieveMoviesListStart() }
                //.doOnTerminate { onRetrieveMoviesListFinish() }
                .subscribe({ result -> onRetrieveMoviesListSuccess(result) },
                        { error -> onRetrieveOnlineMoviesListError(error) }
                )
    }


    override fun onCleared() {
        super.onCleared()
if(!onlineSubscription.isDisposed)
        onlineSubscription.dispose()
if(!offlineSubscription.isDisposed)
        offlineSubscription.dispose()
    }

    private fun onRetrieveMoviesListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveMoviesListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveMoviesListSuccess(postList: List<Movie>) {
        movieListAdapter.updateMoviesList(postList)
    }

    private fun onRetrieveOnlineMoviesListError(error: Throwable) {
        errorMessage.value = R.string.get_online_movies_error
        Log.e(tag, error.message)
    }
}