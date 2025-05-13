package com.mmust.demeter.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.mmust.demeter.data.local.dao.DeviceDao
import com.mmust.demeter.data.local.dao.GreenhouseDao
import com.mmust.demeter.data.local.database.GreenhouseDatabase
import com.mmust.demeter.data.remote.api.AuthApi
import com.mmust.demeter.data.remote.api.GreenhouseApi
import com.mmust.demeter.data.remote.api.WebSocketService
import com.mmust.demeter.data.repository.AuthRepositoryImpl
import com.mmust.demeter.data.repository.GreenhouseRepositoryImpl
//import com.mmust.demeter.data.source.FirebaseAuthSource
import com.mmust.demeter.domain.repository.AuthRepository
import com.mmust.demeter.domain.repository.GreenhouseRepository
import com.mmust.demeter.domain.usecases.GetDevicesUseCase
import com.mmust.demeter.domain.usecases.GetGreenhouseByIdUseCase
import com.mmust.demeter.domain.usecases.GetGreenhousesUseCase
import com.mmust.demeter.domain.usecases.LoginUseCase
import com.mmust.demeter.domain.usecases.RefreshDevicesUseCase
import com.mmust.demeter.domain.usecases.RefreshGreenhousesUseCase
import com.mmust.demeter.domain.usecases.SaveGreenhousesUseCase
import com.mmust.demeter.domain.usecases.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*@Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }*/

    /*@Provides
    @Singleton
    fun provideFirebaseAuthSource(firebaseAuth: FirebaseAuth): FirebaseAuthSource {
        return FirebaseAuthSource(firebaseAuth)
    }*/

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl("https://a7be-80-187-84-199.ngrok-free.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideSignUpUseCase(authRepository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            File(context.filesDir, "session_prefs.preferences_pb")
        }
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GreenhouseDatabase {
        return Room.databaseBuilder(
            context,
            GreenhouseDatabase::class.java,
            "greenhouse_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGreenhouseDao(database: GreenhouseDatabase): GreenhouseDao {
        return database.greenhouseDao()
    }

    @Provides
    @Singleton
    fun provideDeviceDao(database: GreenhouseDatabase): DeviceDao {
        return database.deviceDao()
    }

    @Provides
    @Singleton
    fun provideGreenhouseApi(): GreenhouseApi {
        return Retrofit.Builder()
            .baseUrl("https://a7be-80-187-84-199.ngrok-free.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GreenhouseApi::class.java)
    }


    @Provides
    @Singleton
    fun provideGreenhouseRepository(
        api: GreenhouseApi,
        greenhouseDao: GreenhouseDao,
        deviceDao: DeviceDao
    ): GreenhouseRepository {
        return GreenhouseRepositoryImpl(api, greenhouseDao, deviceDao)
    }


    @Provides
    @Singleton
    fun provideGetDevicesUseCase(greenhouseRepository: GreenhouseRepository): GetDevicesUseCase {
        return GetDevicesUseCase(greenhouseRepository)
    }

    @Provides
    @Singleton
    fun provideRefreshDevicesUseCase(greenhouseRepository: GreenhouseRepository): RefreshDevicesUseCase {
        return RefreshDevicesUseCase(greenhouseRepository)
    }

    @Provides
    @Singleton
    fun provideGetGreenHousesUseCase(greenhouseRepository: GreenhouseRepository): GetGreenhousesUseCase {
        return GetGreenhousesUseCase(greenhouseRepository)
    }

    @Provides
    @Singleton
    fun provideGetGreenhouseByIdUseCase(greenhouseRepository: GreenhouseRepository): GetGreenhouseByIdUseCase {
        return GetGreenhouseByIdUseCase(greenhouseRepository)
    }

    @Provides
    @Singleton
    fun provideRefreshGreenhouseUseCase(greenhouseRepository: GreenhouseRepository): RefreshGreenhousesUseCase {
       return RefreshGreenhousesUseCase(greenhouseRepository)
    }

    @Provides
    @Singleton
    fun provideSaveGreenhouseUseCase(greenhouseRepository: GreenhouseRepository ): SaveGreenhousesUseCase {
        return SaveGreenhousesUseCase(greenhouseRepository)
    }

    @Provides
    @Singleton
    fun provideWebSocketService(): WebSocketService {
        return WebSocketService()
    }
}