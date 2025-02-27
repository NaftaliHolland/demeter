package com.mmust.demeter.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.mmust.demeter.data.repository.AuthRepositoryImpl
import com.mmust.demeter.data.repository.PreferencesRepositoryImpl
import com.mmust.demeter.data.source.FirebaseAuthSource
import com.mmust.demeter.domain.repository.AuthRepository
import com.mmust.demeter.domain.repository.PreferencesRepository
import com.mmust.demeter.domain.usecases.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferencesRepository(application: Application): PreferencesRepository {
        return PreferencesRepositoryImpl(context = application)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthSource(firebaseAuth: FirebaseAuth): FirebaseAuthSource {
        return FirebaseAuthSource(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuthSource: FirebaseAuthSource): AuthRepository {
        return AuthRepositoryImpl(firebaseAuthSource)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            File(context.filesDir, "session_prefs")
        }

    }
}