package com.samuelcabezas.rmoviesapp.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.samuelcabezas.rmoviesapp.models.entity.Movie

@Dao
interface MovieDao {

    @get:Query("SELECT * FROM Movie")
    val movies: List<Movie>

    @Query("SELECT * FROM Movie where category =:category")
    fun selectMoviesByCategory(category: String): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //@Insert
    fun insertMovies(vararg movies: Movie)
}
