package com.oguzhan.karnavalcase.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.oguzhan.karnavalcase.MainActivity
import com.oguzhan.karnavalcase.R
import com.oguzhan.karnavalcase.base.BaseFragment
import com.oguzhan.karnavalcase.databinding.FragmentMovieDetailBinding
import com.oguzhan.karnavalcase.model.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding,MovieDetailViewModel,MainActivity>(FragmentMovieDetailBinding::inflate) {

    override val viewModel: MovieDetailViewModel by viewModels()
    override fun initializeListeners() {


        val bundle = arguments

        val movieItemId = bundle?.getLong("movieId")

        movieItemId?.let {
            viewModel.getPopularMovieById(it)
        }


    }

    override fun observeEvents() {


        viewModel.popularMoviesById.observe(viewLifecycleOwner){ movie->

            when(movie){

                is Resource.Success ->{ movie

                    movie.data?.let {

                        binding.text.text = it.overview
                    }
                }
                is Resource.Loading ->{}
                is Resource.Error ->{}
            }



        }
    }


}