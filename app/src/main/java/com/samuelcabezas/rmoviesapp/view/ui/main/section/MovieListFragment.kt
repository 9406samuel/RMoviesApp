package com.samuelcabezas.rmoviesapp.view.ui.main.section

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samuelcabezas.rmoviesapp.R
import com.samuelcabezas.rmoviesapp.databinding.FragmentMainBinding
import com.samuelcabezas.rmoviesapp.factory.ViewModelFactory
import com.samuelcabezas.rmoviesapp.utils.Constants.CATEGORY
import com.samuelcabezas.rmoviesapp.utils.Constants.MOVIE
import com.samuelcabezas.rmoviesapp.view.ui.details.MovieDetailsActivity
import com.samuelcabezas.rmoviesapp.view.ui.main.SharedViewModel

class MovieListFragment : Fragment() {

    private lateinit var movieListViewModel: MovieListViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: FragmentMainBinding
    private var movieCategory: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieListViewModel = ViewModelProviders.of(this, ViewModelFactory(context!!))
                .get(MovieListViewModel::class.java)

        activity?.let {
            sharedViewModel = ViewModelProviders.of(it).get(SharedViewModel::class.java)
        }
        movieCategory = arguments!!.getInt(CATEGORY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.rvMoviesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        movieListViewModel.errorMessage.observe(this, Observer { errorMessage ->
            sharedViewModel.reloadData.postValue(errorMessage)
        })
        movieListViewModel.loadMovieDetails.observe(this, Observer { movie ->
            val i = Intent(activity, MovieDetailsActivity::class.java)
            i.putExtra(MOVIE, movie)
            startActivity(i)
        })
        binding.viewModel = movieListViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieListViewModel.loadMoviesList(movieCategory)
    }

    fun loadData() {
        if (::movieListViewModel.isInitialized) {
            movieListViewModel.loadMoviesList(movieCategory)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(category: Int): MovieListFragment {
            return MovieListFragment().apply {
                arguments = Bundle().apply {
                    putInt(CATEGORY, category)
                }
            }
        }
    }
}