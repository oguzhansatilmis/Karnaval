package com.oguzhan.karnavalcase.presentation.movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.util.query
import com.oguzhan.karnavalcase.MainActivity
import com.oguzhan.karnavalcase.R
import com.oguzhan.karnavalcase.base.BaseFragment
import com.oguzhan.karnavalcase.databinding.FragmentMovieBinding
import com.oguzhan.karnavalcase.extensions.doOnTextChanged
import com.oguzhan.karnavalcase.model.Movie
import com.oguzhan.karnavalcase.model.MovieResponse
import com.oguzhan.karnavalcase.model.Resource
import com.oguzhan.movielist.TotalMovieList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class MovieFragment :
    BaseFragment<FragmentMovieBinding, MovieViewModel, MainActivity>(FragmentMovieBinding::inflate) {

    override val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    private var page = 1

    override fun initializeListeners() {

        listIconListener()
        searchEditTextListener()
        moreButtonListener()



    }

    private fun moreButtonListener(){
        binding.moreBtn.setOnClickListener {
            page++
            viewModel.getPopularMovies(page)
        }
    }
    private fun listIconListener(){

        binding.apply {

            listIcon.setOnClickListener {

                if (listIcon.isSelected) {
                    setupAdapter(TotalMovieList.totalMovieList.flatMap { it.results }.toMutableList(),"list")

                } else {
                    setupAdapter(TotalMovieList.totalMovieList.flatMap { it.results }.toMutableList(),"grid")

                }
                listIcon.isSelected = !listIcon.isSelected
            }
        }
    }

    private fun searchEditTextListener(){
        binding.searchEditText.doOnTextChanged { query ->
            if (query.isEmpty()) {
                viewModel.getPopularMovies(page)
                binding.moreBtn.visibility = View.VISIBLE
            }
            else{
                binding.moreBtn.visibility = View.GONE
                viewModel.searchMovie(query)
            }
        }
    }
    override fun observeEvents() {

          popularMovies()

          searchMovies()
    }

    private fun searchMovies(){
        viewModel.searchMovies.observe(viewLifecycleOwner) { movies ->

            when (movies) {

                is Resource.Success -> {
                    activity().hideProgress()
                    movies.data?.let {
                        setupAdapter(it.results,"grid")
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
    private fun  popularMovies(){
        viewModel.getPopularMovies(page)

        viewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            when (movies) {

                is Resource.Success -> {
                    activity().hideProgress()
                    movies.data?.let {
                        addPageIfNotExists(it)

                        if (binding.listIcon.isSelected) {
                            setupAdapter(TotalMovieList.totalMovieList.flatMap { it.results }.toMutableList(),"grid")
                        } else {
                            setupAdapter(TotalMovieList.totalMovieList.flatMap { it.results }.toMutableList(),"list")
                        }
                        binding.moreBtn.visibility = View.VISIBLE
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
        val pageExists = TotalMovieList.totalMovieList.any { it.page == newPage.page }

        if (!pageExists) {
            TotalMovieList.totalMovieList.add(newPage)
        }
    }
    private fun setupAdapter(movieList: MutableList<Movie>,type:String ) {

        movieAdapter = MovieAdapter(movieList)
        binding.apply {
            when(type){
               "grid" ->{  movieRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)}
                "list" ->{ movieRecyclerview.layoutManager = LinearLayoutManager(requireContext()) }
            }
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