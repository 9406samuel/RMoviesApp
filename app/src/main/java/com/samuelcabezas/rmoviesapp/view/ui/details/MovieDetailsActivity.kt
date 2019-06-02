package com.samuelcabezas.rmoviesapp.view.ui.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.samuelcabezas.rmoviesapp.R
import com.samuelcabezas.rmoviesapp.models.entity.Movie
import com.samuelcabezas.rmoviesapp.utils.Api
import com.samuelcabezas.rmoviesapp.utils.Constants
import com.samuelcabezas.rmoviesapp.utils.GlideApp
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        //setSupportActionBar(toolbar)
        initializeUI()
    }

    private fun initializeUI(){

        getMovieFromIntent().backdrop_path?.let {
            GlideApp.with(this).load(Api.getBackdropPath(it))
                    .placeholder(R.drawable.no_image)
                    .into(movie_backdrop)
        }
    }

    private fun getMovieFromIntent(): Movie = intent.getParcelableExtra(Constants.MOVIE) as Movie
}
