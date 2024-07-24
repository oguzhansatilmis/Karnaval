package com.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.oguzhan.karnavalcase.db.MovieDao
import com.oguzhan.karnavalcase.db.MovieDatabase
import com.oguzhan.karnavalcase.model.FavoriteMovie
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class MovieDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database : MovieDatabase
    private lateinit var favoriteMovieDao : MovieDao

    @Before
    fun setup() {
        hiltRule.inject()
        favoriteMovieDao = database.movieDao()
    }

    @After
    fun teardown() {
        database.close()
    }
    @Test
    fun insertFavoriteMovie_getFavoriteMoviesList() = runBlocking {
        val favoriteMovie = FavoriteMovie(id = 123L,movieId = 1L)
        favoriteMovieDao.insertFavoriteMovie(favoriteMovie)

        val movies = favoriteMovieDao.getFavoriteMoviesList()
        assertEquals(1, movies.size)
    }

    @Test
    fun isFavoriteMovie_returnsTrue() = runBlocking {
        val favoriteMovie = FavoriteMovie(id = 123L,movieId = 1L)
        favoriteMovieDao.insertFavoriteMovie(favoriteMovie)

        val isFavorite = favoriteMovieDao.isFavoriteMovie(1L)
        assertTrue(isFavorite)
    }

    @Test
    fun isFavoriteMovie_returnsFalse() = runBlocking {
        val isFavorite = favoriteMovieDao.isFavoriteMovie(1L)
        assertFalse(isFavorite)
    }

    @Test
    fun deleteFavoriteMovieById() = runBlocking {
        val favoriteMovie = FavoriteMovie(id = 123L,movieId = 1L)
        favoriteMovieDao.insertFavoriteMovie(favoriteMovie)

        favoriteMovieDao.deleteFavoriteMovieById(1L)

        val movies = favoriteMovieDao.getFavoriteMoviesList()
        assertTrue(movies.isEmpty())
    }



}