package com.oguzhan.karnavalcase.presentation.movie

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.oguzhan.karnavalcase.MainActivity
import com.oguzhan.karnavalcase.R
import com.oguzhan.karnavalcase.base.BaseFragment
import com.oguzhan.karnavalcase.databinding.FragmentMovieBinding
import com.oguzhan.karnavalcase.model.Movie
import com.oguzhan.karnavalcase.model.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieFragment :
    BaseFragment<FragmentMovieBinding, MovieViewModel, MainActivity>(FragmentMovieBinding::inflate) {

    override val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun initializeListeners() {


        viewModel.getPopularMovies()


    }

    override fun observeEvents() {


        viewModel.popularMovies.observe(viewLifecycleOwner){ movies ->

            when(movies){

                is Resource.Success ->{ movies

                    movies.data?.let {
                           setupAdapter(it.results)
                       }
                }
                is Resource.Loading ->{}
                is Resource.Error ->{}
            }
        }
    }
    private fun setupAdapter(movieList :List<Movie>){

        movieAdapter = MovieAdapter(movieList)

        binding.apply {
            movieRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            movieRecyclerview.adapter = movieAdapter
        }

        adapterListener()

    }
    private fun adapterListener(){

        movieAdapter.movieIdListener = {movieId->

            val bundle = Bundle()
            bundle.apply {
                putLong("movieId", movieId)
            }

            findNavController().navigate(R.id.action_movieFragment_to_movieDetailFragment,bundle)

        }
    }

}