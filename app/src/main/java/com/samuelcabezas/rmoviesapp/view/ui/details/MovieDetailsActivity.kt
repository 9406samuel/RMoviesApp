package com.samuelcabezas.rmoviesapp.view.ui.details

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.samuelcabezas.rmoviesapp.R
import com.samuelcabezas.rmoviesapp.models.entity.Movie
import com.samuelcabezas.rmoviesapp.utils.Api
import com.samuelcabezas.rmoviesapp.utils.Constants.MOVIE
import com.samuelcabezas.rmoviesapp.utils.GlideApp
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        initializeUI()
    }

    private fun initializeUI(){

        getMovieFromIntent().poster_path?.let {
            GlideApp.with(this).load(Api.getPosterPath(it))
                   .into(iv_movie_poster)
        }
    }

    private fun getMovieFromIntent(): Movie = intent.getParcelableExtra(MOVIE) as Movie

}
