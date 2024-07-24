package com.oguzhan.karnavalcase.presentation.detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.oguzhan.karnavalcase.MainActivity
import com.oguzhan.karnavalcase.R
import com.oguzhan.karnavalcase.base.BaseFragment
import com.oguzhan.karnavalcase.databinding.FragmentMovieDetailBinding
import com.oguzhan.karnavalcase.extensions.loadUrl
import com.oguzhan.karnavalcase.model.Resource
import com.oguzhan.movielist.TotalMovieList
import com.oguzhan.movielist.TotalMovieList.totalMovieList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel, MainActivity>(
        FragmentMovieDetailBinding::inflate
    ) {
    override val viewModel: MovieDetailViewModel by viewModels()


    override fun initializeListeners() {
        initializeBundle()
        navigateMovieFragment()
    }

    override fun observeEvents() {
        popularMoviesByIdListener()
    }
    private fun initializeBundle(){
        val bundle = arguments
        val  movieItemId = bundle?.getLong("movieId")
        movieItemId?.let {
            viewModel.getPopularMovieById(it)
            favoriteMovieListener(it)
        }
    }
    private fun favoriteMovieListener(movieItemId:Long){
        viewModel.viewModelScope.launch {

            val isFavorite = viewModel.isFavoriteMovie(movieItemId)
            binding.starIcon.isSelected = isFavorite

            binding.starIcon.setOnClickListener {
                if (binding.starIcon.isSelected) {
                    viewModel.deleteFavoriteMovieById(movieItemId)
                } else {
                    viewModel.addedToFavoriteMovie(movieItemId)

                }
                binding.starIcon.isSelected = !binding.starIcon.isSelected
            }
        }

    }

    private fun popularMoviesByIdListener() {
        viewModel.popularMoviesById.observe(viewLifecycleOwner) { movie ->

            when (movie) {
                is Resource.Success -> {
                    activity().hideProgress()
                    movie.data?.let {
                        binding.apply {
                            if (it.posterPath != null)   detailMovieImage.loadUrl(it.posterPath) else   detailMovieImage.setBackgroundResource(R.drawable.image_not_found)
                            detailMovieTitle.text = it.title
                            detailMovieOverview.text = it.overview
                            detailVoteCountText.text = it.voteCount.toString()
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
    private fun navigateMovieFragment() {
        binding.navigateIcon.setOnClickListener {
            findNavController().navigate(R.id.action_movieDetailFragment_to_movieFragment)
        }
    }
}


