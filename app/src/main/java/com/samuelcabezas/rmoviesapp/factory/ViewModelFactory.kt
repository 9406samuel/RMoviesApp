package com.samuelcabezas.rmoviesapp.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.content.Context
import com.samuelcabezas.rmoviesapp.room.AppDatabase
import com.samuelcabezas.rmoviesapp.view.main.category.MovieListViewModel

class ViewModelFactory(private val context: Context, private val category: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            val db = Room.databaseBuilder(context, AppDatabase::class.java, "movies").build()
            @Suppress("UNCHECKED_CAST")
            return MovieListViewModel(db.movieDao(), category) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}