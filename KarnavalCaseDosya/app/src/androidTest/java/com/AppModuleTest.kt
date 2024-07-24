package com

import android.content.Context
import androidx.room.Room
import com.oguzhan.karnavalcase.db.MovieDatabase
import com.oguzhan.karnavalcase.model.FavoriteMovie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModuleTest {
    @Provides
    @Named("testDatabase")
    @Singleton
    fun provideInMemoryRoom(@ApplicationContext context : Context) =
        Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}