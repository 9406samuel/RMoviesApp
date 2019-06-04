package com.samuelcabezas.rmoviesapp.view.main

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.samuelcabezas.rmoviesapp.R
import com.samuelcabezas.rmoviesapp.view.main.category.MovieListFragment

private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3
)

class MainPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment =
            MovieListFragment.newInstance(context.resources.getString(TAB_TITLES[position]))

    override fun getPageTitle(position: Int): CharSequence? =
            context.resources.getString(TAB_TITLES[position])

    override fun getCount(): Int = 3
}