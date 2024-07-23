package com.oguzhan.karnavalcase.presentation.movie

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.oguzhan.karnavalcase.MainActivity
import com.oguzhan.karnavalcase.R
import com.oguzhan.karnavalcase.base.BaseFragment
import com.oguzhan.karnavalcase.databinding.FragmentMovieBinding
import com.oguzhan.karnavalcase.model.Movie
import com.oguzhan.karnavalcase.model.MovieResponse
import com.oguzhan.karnavalcase.model.Resource
import com.oguzhan.movielist.TotalMovieList
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieFragment :
    BaseFragment<FragmentMovieBinding, MovieViewModel, MainActivity>(FragmentMovieBinding::inflate) {

    override val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    private var page = 1


    override fun initializeListeners() {

        binding.buton.setOnClickListener{

            setupAdapter(TotalMovieList.totalMovieList.flatMap { it.results }.toMutableList())

        }

        binding.buton2.setOnClickListener{

            setupAdapterGrid(TotalMovieList.totalMovieList.flatMap { it.results }.toMutableList())

        }

        viewModel.getPopularMovies(page)
        binding.moreBtn.setOnClickListener {
           page++
           viewModel.getPopularMovies(page)
        }
    }

    override fun observeEvents() {

        viewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            when (movies) {

                is Resource.Success -> {
                    activity().hideProgress()
                    movies.data?.let {
                        addPageIfNotExists(it)
                        setupAdapter(TotalMovieList.totalMovieList.flatMap { it.results }.toMutableList())
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

    private fun addPageIfNotExists(newPage: MovieResponse) {
        // Sayfa numarasına göre kontrol edelim
        val pageExists = TotalMovieList.totalMovieList.any { it.page == newPage.page }

        if (!pageExists) {
            TotalMovieList.totalMovieList.add(newPage)
            println("Page ${newPage.page} added. Total pages: ${TotalMovieList.totalMovieList.size}")
        } else {
            println("Page ${newPage.page} already exists. No addition performed.")
        }
    }

    private fun setupAdapter(movieList: MutableList<Movie>) {

        movieAdapter = MovieAdapter(movieList)
        binding.apply {
            movieRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            movieRecyclerview.adapter = movieAdapter
        }
        adapterListener()
    }

    private fun setupAdapterGrid(movieList: MutableList<Movie>) {

        movieAdapter = MovieAdapter(movieList)
        binding.apply {
            movieRecyclerview.layoutManager = GridLayoutManager(requireContext(),2)
            movieRecyclerview.adapter = movieAdapter
        }
        adapterListener()
    }

    private fun adapterListener() {
        movieAdapter.movieIdListener = { movieId ->
            val bundle = Bundle()
            bundle.apply {
                putLong("movieId", movieId)
            }
            findNavController().navigate(R.id.action_movieFragment_to_movieDetailFragment, bundle)
        }
    }
}