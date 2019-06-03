package com.samuelcabezas.rmoviesapp.view.ui.main.section

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samuelcabezas.rmoviesapp.R
import com.samuelcabezas.rmoviesapp.databinding.FragmentMainBinding
import com.samuelcabezas.rmoviesapp.factory.ViewModelFactory
import com.samuelcabezas.rmoviesapp.utils.Constants
import com.samuelcabezas.rmoviesapp.utils.Constants.CATEGORY
import com.samuelcabezas.rmoviesapp.view.ui.details.MovieDetailsActivity
import com.samuelcabezas.rmoviesapp.view.ui.main.MainActivity
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieListFragment : Fragment(), Parcelable {

    private lateinit var movieListViewModel: MovieListViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var movieCategory: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieListViewModel = ViewModelProviders.of(
                this, ViewModelFactory(context!!, arguments!!.getString(CATEGORY)))
                .get(MovieListViewModel::class.java)
        movieCategory = arguments!!.getString(CATEGORY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.rvMoviesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        movieListViewModel.movieListAdapter.onItemClick = { movie ->
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra(Constants.MOVIE, movie)
            startActivity(intent)
        }

        movieListViewModel.errorMessage.observe(this, Observer { errorMessage ->
            val parent = activity as MainActivity
            if (errorMessage != null) {
                parent.addCurrentFragment(this)
                parent.showError(errorMessage)
            } else parent.hideError()
        })

        binding.viewModel = movieListViewModel
        return binding.root
    }

    fun loadDataFromAPI() {
        if (::movieListViewModel.isInitialized) {
            movieListViewModel.loadMoviesListFromAPI(movieCategory)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(category: String): MovieListFragment {
            return MovieListFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY, category)
                }
            }
        }
    }
}