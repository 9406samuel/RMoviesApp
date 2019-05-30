package com.samuelcabezas.rmoviesapp.view.ui.main.section

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import android.view.View
import com.samuelcabezas.rmoviesapp.R
import com.samuelcabezas.rmoviesapp.models.entity.Movie
import com.samuelcabezas.rmoviesapp.room.MovieDao
import com.samuelcabezas.rmoviesapp.api.ApiClient
import com.samuelcabezas.rmoviesapp.utils.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MovieListViewModel(private val movieDao: MovieDao) : ViewModel() {

    private val tag : String = MovieListViewModel::class.java.simpleName

    val movieListAdapter: MovieListAdapter =
        MovieListAdapter()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }

    private lateinit var subscription: Disposable

    init {
        loadPosts()
    }

    private fun loadPosts(){

        subscription = Observable.fromCallable { movieDao.movies }
            .concatMap { dbMoviesList ->
                if(dbMoviesList.isEmpty()){
                    ApiClient.getApiClient().getCurrentPopularMovies(Constants.API_KEY_VALUE).concatMap {
                            apiMoviesList -> movieDao.insertMovies(*(apiMoviesList.getMovieResults()).toTypedArray())
                    Observable.just(apiMoviesList.getMovieResults())}
                }else{
                    Observable.just(dbMoviesList)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{onRetrievePostListStart()}
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe({ result -> onRetrievePostListSuccess(result) },
                {error -> onRetrievePostListError(error)})

    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun onRetrievePostListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList:List<Movie>){
        //onRetrievePostListFinish()
        movieListAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListError(error: Throwable) {
        errorMessage.value = R.string.get_movies_error
        Log.e(tag, error.message)
    }
}