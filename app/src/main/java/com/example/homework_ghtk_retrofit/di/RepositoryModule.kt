package com.example.homework_ghtk_retrofit.di

import com.example.homework_ghtk_retrofit.repository.PokemonRepository
import com.example.homework_ghtk_retrofit.repository.PokemonRepositoryImpl
import com.example.homework_ghtk_retrofit.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providePokemonRepository(
        apiService: ApiService
    ): PokemonRepository {
        return PokemonRepositoryImpl(apiService)
    }


}