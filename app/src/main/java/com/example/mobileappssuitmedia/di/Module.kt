package com.example.mobileappssuitmedia.di

import android.app.Application
import androidx.room.Room
import com.example.mobileappssuitmedia.local.LocalUserDatabase
import com.example.mobileappssuitmedia.network.ApiImpl
import com.example.mobileappssuitmedia.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun provideKtor(): HttpClient {
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json()
            }
        }
        return client
    }

    @Provides
    @Singleton
    fun provideApiData(client: HttpClient): ApiService {
        return ApiImpl(client)
    }

    @Provides
    @Singleton
    fun providesRoomDatabase(context: Application): LocalUserDatabase {
        return Room.databaseBuilder(
            context, LocalUserDatabase::class.java,
            "user_storage"
        ).build()
    }
}