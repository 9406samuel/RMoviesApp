package com.samuelcabezas.rmoviesapp.view.ui.main.section

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import com.samuelcabezas.rmoviesapp.R
import com.samuelcabezas.rmoviesapp.models.entity.Movie
import com.samuelcabezas.rmoviesapp.databinding.MovieListItemBinding


class MovieListAdapter(val selected : (Movie) -> Unit) : RecyclerView.Adapter<MovieListAdapter.MoviesHolder>() {

    private lateinit var moviesList: List<Movie>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val binding: MovieListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.movie_list_item, parent, false)
        return MoviesHolder(binding)
    }

    override fun getItemCount(): Int {
        return if(::moviesList.isInitialized) moviesList.size else 0
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    fun updatePostList(moviesList: List<Movie>) {
        this.moviesList = moviesList
        notifyDataSetChanged()
    }

    inner class MoviesHolder(private val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val viewModel = MovieViewModel()
        fun bind(movie: Movie) {
            viewModel.bind(movie)
            binding.movieItemContainer.setOnClickListener({
                selected.invoke(movie)
            })
            binding.viewModel =viewModel
        }
    }
}