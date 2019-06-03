package com.samuelcabezas.rmoviesapp.view.ui.main.section

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Base64
import android.util.Log
import android.view.View
import com.samuelcabezas.rmoviesapp.R
import com.samuelcabezas.rmoviesapp.api.ApiClient
import com.samuelcabezas.rmoviesapp.models.entity.Movie
import com.samuelcabezas.rmoviesapp.room.MovieDao
import com.samuelcabezas.rmoviesapp.utils.Api
import com.samuelcabezas.rmoviesapp.utils.Constants.POPULAR
import com.samuelcabezas.rmoviesapp.utils.Constants.TOP_RATED
import com.samuelcabezas.rmoviesapp.utils.Constants.UPCOMING
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MovieListViewModel(private val movieDao: MovieDao, private var category: String) : ViewModel() {

    private val tag: String = MovieListViewModel::class.java.simpleName

    val movieListAdapter: MovieListAdapter = MovieListAdapter()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val loadMoviesListFromAPI: ((String) -> Unit) = {
        category = it
        loadOnlineMoviesList()
    }
    private val moviesList: ArrayList<Movie> = ArrayList()

    private lateinit var onlineSubscription: Disposable
    private lateinit var offlineSubscription: Disposable

    init {
        loadOfflineMoviesList()
        loadOnlineMoviesList()
    }

    private fun loadOnlineMoviesList() {

        onlineSubscription = Observable.fromCallable {
            (when (category) {
                POPULAR -> ApiClient.getApiClient(Api.API_BASE_URL).getCurrentPopularMovies()
                TOP_RATED -> ApiClient.getApiClient(Api.API_BASE_URL).getTopRatedMovies()
                UPCOMING -> ApiClient.getApiClient(Api.API_BASE_URL).getUpcomingMovies()
                else -> ApiClient.getApiClient(Api.API_BASE_URL).discoverMovies()
            })
        }.concatMap { apiResponse ->
            apiResponse.concatMap { movieResponse ->
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

    private fun loadOfflineMoviesList() {

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
                .subscribe({ result -> onRetrieveMoviesListSuccess(result) },
                        { error -> onRetrieveOnlineMoviesListError(error) }
                )
    }

    private fun getImagesFromAPI(baseUrl: String, movie: Movie) {

        val client = OkHttpClient()

        val request = Request.Builder()
                .url("$baseUrl${movie.poster_path}")
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call?, e: IOException?) {
                System.out.println("request failed: " + e?.message)
            }

            override fun onResponse(call: okhttp3.Call?, response: okhttp3.Response?) {
                movie.posterImage = Base64.encodeToString(response?.body()?.bytes(), Base64.DEFAULT)
                moviesList.add(movie)
            }
        })

    }

    override fun onCleared() {
        super.onCleared()
        if (!onlineSubscription.isDisposed)
            onlineSubscription.dispose()
        if (!offlineSubscription.isDisposed)
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
        //TODO: Save images locally as files and load them from Glide in Views
        // postList.forEach { m -> getImagesFromAPI(Api.BASE_POSTER_URL, m) }
        movieListAdapter.updateMoviesList(postList)
    }

    private fun onRetrieveOnlineMoviesListError(error: Throwable) {
        errorMessage.value = R.string.get_online_movies_error
        Log.e(tag, error.message)
    }
}