package com.samuelcabezas.rmoviesapp.view.ui.main

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.samuelcabezas.rmoviesapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        val sectionsPagerAdapter = MainPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        viewPager.offscreenPageLimit = 3
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}