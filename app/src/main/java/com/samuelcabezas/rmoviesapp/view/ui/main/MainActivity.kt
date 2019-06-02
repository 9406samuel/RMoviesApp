package com.samuelcabezas.rmoviesapp.view.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.samuelcabezas.rmoviesapp.R
import com.samuelcabezas.rmoviesapp.R.id.view_pager
import com.samuelcabezas.rmoviesapp.view.ui.main.section.MovieListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var sectionsPagerAdapter: MainPagerAdapter
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        sectionsPagerAdapter = MainPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        view_pager.offscreenPageLimit = 3
        tabs.setupWithViewPager(view_pager)

        viewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        viewModel.reloadData.observe(this, Observer {errorMessage ->
            if (errorMessage != null){
                showError(errorMessage)
            } else hideError()
        })
    }

    private fun loadMoviesData(){
        val pagerAdapter: MainPagerAdapter = view_pager.adapter as MainPagerAdapter
        pagerAdapter.fragmentsMap.forEach { i, fragment ->
            if(fragment is MovieListFragment){
                fragment.loadData()
                Toast.makeText(this, "f - $i", Toast.LENGTH_LONG).show()
            }
        }
    }

     private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(tabs, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, { loadMoviesData() })
        errorSnackbar?.show()
    }

     private fun hideError() {
        errorSnackbar?.dismiss()
    }
}