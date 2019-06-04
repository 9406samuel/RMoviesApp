package com.samuelcabezas.rmoviesapp.view.main.category

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.samuelcabezas.rmoviesapp.R
import com.samuelcabezas.rmoviesapp.databinding.MovieListItemBinding
import com.samuelcabezas.rmoviesapp.models.entity.Movie

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MoviesHolder>() {

    private lateinit var moviesList: List<Movie>
    var onItemClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val binding: MovieListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.movie_list_item, parent, false)
        binding.movieImage.drawable
        return MoviesHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (::moviesList.isInitialized) moviesList.size else 0
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    fun updateMoviesList(moviesList: List<Movie>) {
        this.moviesList = moviesList
        notifyDataSetChanged()
    }

    inner class MoviesHolder(private val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val viewModel = MovieViewModel()
        fun bind(movie: Movie) {
            viewModel.bind(movie)
            binding.movieItemContainer.setOnClickListener({
                onItemClick?.invoke(movie)
            })
            binding.viewModel = viewModel
        }
    }
}