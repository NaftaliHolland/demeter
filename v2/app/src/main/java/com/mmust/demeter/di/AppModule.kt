package com.mmust.demeter.di

import android.app.Application
import com.mmust.demeter.data.repository.PreferencesRepositoryImpl
import com.mmust.demeter.domain.repository.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferencesRepository(application: Application): PreferencesRepository {
        return PreferencesRepositoryImpl(context = application)
    }
}