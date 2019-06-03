package com.samuelcabezas.rmoviesapp.view.ui.details

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.samuelcabezas.rmoviesapp.R
import com.samuelcabezas.rmoviesapp.databinding.ActivityMovieDetailsBinding
import com.samuelcabezas.rmoviesapp.models.entity.Movie
import com.samuelcabezas.rmoviesapp.utils.Constants
import com.samuelcabezas.rmoviesapp.view.ui.main.section.MovieViewModel

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        movieViewModel.bind(getMovieFromIntent())
        binding.viewModel = movieViewModel
    }

    private fun getMovieFromIntent(): Movie = intent.getParcelableExtra(Constants.MOVIE) as Movie
}
