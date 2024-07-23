package com.oguzhan.karnavalcase.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.oguzhan.karnavalcase.MainActivity
import com.oguzhan.karnavalcase.R
import com.oguzhan.karnavalcase.base.BaseFragment
import com.oguzhan.karnavalcase.databinding.FragmentMovieDetailBinding
import com.oguzhan.karnavalcase.extensions.loadUrl
import com.oguzhan.karnavalcase.model.Movie
import com.oguzhan.karnavalcase.model.Resource
import com.oguzhan.karnavalcase.presentation.movie.MovieViewModel
import com.oguzhan.movielist.TotalMovieList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel, MainActivity>(
        FragmentMovieDetailBinding::inflate
    ) {

    override val viewModel: MovieDetailViewModel by viewModels()


    override fun initializeListeners() {

        val bundle = arguments
        val movieItemId = bundle?.getLong("movieId")

        movieItemId?.let {
            viewModel.getPopularMovieById(it)

            viewModel.viewModelScope.launch {
                val result = viewModel.isFavoriteMovie(movieItemId)
                binding.favoriteStar.isSelected = result
            }
            binding.favoriteStar.setOnClickListener {

                if (binding.favoriteStar.isSelected) {

                    viewModel.deleteFavoriteMovieById(movieItemId)

                    TotalMovieList.totalMovieList.forEach {
                        it.results.forEach { movie ->

                            if (movie.id == movieItemId) {
                                movie.isFavorite = false

                            }

                        }
                        println("item silindi")
                    }

                } else {
                    viewModel.addedToFavoriteMovie(movieItemId)

                    TotalMovieList.totalMovieList.forEach {
                        it.results.forEach { movie ->

                            if (movie.id == movieItemId) {
                                movie.isFavorite = true

                            }

                        }
                        println("item eklendi")
                    }
                }

                binding.favoriteStar.isSelected = !binding.favoriteStar.isSelected
            }
        }
    }
    override fun observeEvents() {

        viewModel.popularMoviesById.observe(viewLifecycleOwner) { movie ->

            when (movie) {
                is Resource.Success -> {
                    activity().hideProgress()
                    movie.data?.let {

                        binding.apply {
                            detailMovieImage.loadUrl(it.backdropPath)
                            detailMovieTitle.text = it.title
                            detailMovieOverview.text = it.overview
                        }
                    }
                }
                is Resource.Loading -> {
                    activity().showProgress()
                }

                is Resource.Error -> {
                    activity().hideProgress()
                }
            }
        }
    }

}