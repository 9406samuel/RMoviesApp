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
import com.samuelcabezas.rmoviesapp.view.ui.main.section.MovieListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var viewPager: ViewPager
    private lateinit var sectionsPagerAdapter: MainPagerAdapter
    private lateinit var tabs: TabLayout
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.view_pager)
        sectionsPagerAdapter = MainPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        viewPager.offscreenPageLimit = 3
        tabs = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        viewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        viewModel.reloadData.observe(this, Observer {errorMessage ->
            if (errorMessage != null){
                showError(errorMessage)
            } else hideError()
        })
    }

    private fun loadMoviesData(){
        val pagerAdapter: MainPagerAdapter = viewPager.adapter as MainPagerAdapter
        pagerAdapter.fragmentsMap.forEach { i, fragment ->
            if(fragment is MovieListFragment){
                fragment.loadData()
                Toast.makeText(this, "f - $i", Toast.LENGTH_LONG).show()
            }
        }
    }

     private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(tabs, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, { view -> loadMoviesData() })
        errorSnackbar?.show()
    }

     private fun hideError() {
        errorSnackbar?.dismiss()
    }
}