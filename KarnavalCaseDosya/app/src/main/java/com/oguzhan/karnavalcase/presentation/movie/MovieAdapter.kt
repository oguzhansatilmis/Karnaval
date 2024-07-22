package com.oguzhan.karnavalcase.presentation.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oguzhan.karnavalcase.databinding.MovieItemBinding
import com.oguzhan.karnavalcase.extensions.loadUrl
import com.oguzhan.karnavalcase.model.Movie

class MovieAdapter(val movieList: List<Movie>):RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    var movieIdListener: ((id: Long) -> Unit?)? = null

    inner class ViewHolder(val binding: MovieItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        bind(holder, position)

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    private fun bind(holder: ViewHolder,position: Int){

        val movieItem = movieList[position]

        holder.binding.movieImage.loadUrl(movieItem.backdropPath)
        holder.binding.movieTitle.text = movieItem.title

        holder.binding.movieImage.setOnClickListener {

            movieIdListener?.invoke(movieItem.id)
        }

    }
}