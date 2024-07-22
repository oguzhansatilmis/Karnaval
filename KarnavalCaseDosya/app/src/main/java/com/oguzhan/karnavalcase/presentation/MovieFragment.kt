package com.oguzhan.karnavalcase.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.oguzhan.karnavalcase.MainActivity
import com.oguzhan.karnavalcase.R
import com.oguzhan.karnavalcase.base.BaseFragment
import com.oguzhan.karnavalcase.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding,MovieViewModel,MainActivity>(FragmentMovieBinding::inflate) {

    override val viewModel: MovieViewModel by viewModels()
    override fun initializeListeners() {


        viewModel.fonksiyon()
    }

    override fun observeEvents() {


    }


}