package com.samuelcabezas.rmoviesapp.view.ui.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.samuelcabezas.rmoviesapp.R
import com.samuelcabezas.rmoviesapp.databinding.ActivityMainBinding
import com.samuelcabezas.rmoviesapp.view.ui.main.section.MovieListFragment
import android.widget.Toast
import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction


class MainActivity : AppCompatActivity() {

    private lateinit var errorSnackbar: Snackbar
    private var isSnackbarShowing = false
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragments: ArrayList<MovieListFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragments = ArrayList()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.handler = this
        binding.manager = supportFragmentManager
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList("fragments", fragments)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        fragments = savedInstanceState?.getParcelableArrayList<MovieListFragment>("fragments") as ArrayList<MovieListFragment>
    }

    private fun requestMoviesData() {
        fragments.forEach { f ->
            f.loadDataFromAPI()
        }
    }

    fun addCurrentFragment(currentFragment: MovieListFragment) {
        if (fragments.size >= 0 && fragments.size < binding.tabs.tabCount)
            fragments.add(currentFragment)
    }

    fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(this.findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar.setAction(R.string.retry, { requestMoviesData() })
        if (!isSnackbarShowing) {
            isSnackbarShowing = true
            errorSnackbar.show()
        }
    }

    fun hideError() {
        if (isSnackbarShowing) {
            isSnackbarShowing = false
            errorSnackbar.dismiss()
        }
    }
}