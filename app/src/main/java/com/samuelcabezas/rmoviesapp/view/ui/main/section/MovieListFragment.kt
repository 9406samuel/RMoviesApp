package com.samuelcabezas.rmoviesapp.view.ui.main.section

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.app.Fragment
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import com.samuelcabezas.rmoviesapp.R
import com.samuelcabezas.rmoviesapp.databinding.FragmentMainBinding
import com.samuelcabezas.rmoviesapp.factory.ViewModelFactory

class MovieListFragment : Fragment() {

    private lateinit var movieListViewModel: MovieListViewModel
    private lateinit var binding: FragmentMainBinding
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieListViewModel = ViewModelProviders.of(this, ViewModelFactory(context!!, arguments!!.getInt(CATEGORY)))
            .get(MovieListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.rvMoviesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        movieListViewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = movieListViewModel
        return binding.root
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, movieListViewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"
        private const val CATEGORY = "category"

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