package com.samuelcabezas.rmoviesapp.view.ui.main.section

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import android.view.View
import com.samuelcabezas.rmoviesapp.R
import com.samuelcabezas.rmoviesapp.models.entity.Movie
import com.samuelcabezas.rmoviesapp.room.MovieDao
import com.samuelcabezas.rmoviesapp.api.ApiClient
import com.samuelcabezas.rmoviesapp.utils.Api
import com.samuelcabezas.rmoviesapp.view.ui.main.TAB_TITLES
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MovieListViewModel(private val movieDao: MovieDao) : ViewModel() {

    private val tag: String = MovieListViewModel::class.java.simpleName

    val movieListAdapter: MovieListAdapter = MovieListAdapter{
        loadMovieDetails.value = it
        //Log.d(tag, it.toString())
    }
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val loadMoviesList= this::loadPosts
    val loadMovieDetails: MutableLiveData<Movie> = MutableLiveData()

    private lateinit var subscription: Disposable

    private fun loadPosts(cate: Int) {

        subscription = Observable.fromCallable {
            (when (cate) {
                TAB_TITLES[0] -> ApiClient.getApiClient().getCurrentPopularMovies()
                TAB_TITLES[1] -> ApiClient.getApiClient().getTopRatedMovies()
                TAB_TITLES[2] -> ApiClient.getApiClient().getUpcomingMovies()
                else -> ApiClient.getApiClient().discoverMovies()
            })
        }.concatMap { apiResponse ->
            apiResponse.concatMap { movieResponse ->
                movieDao.insertMovies(*(movieResponse.getMovieResults()).toTypedArray())
                Observable.just(movieResponse.getMovieResults())
            }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrievePostListStart() }
                .doOnTerminate { onRetrievePostListFinish() }
                .subscribe({ result -> onRetrievePostListSuccess(result) },
                        { error -> onRetrievePostListError(error) })
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList: List<Movie>) {
        movieListAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListError(error: Throwable) {
        errorMessage.value = R.string.get_movies_error
        Log.e(tag, error.message)
    }
}