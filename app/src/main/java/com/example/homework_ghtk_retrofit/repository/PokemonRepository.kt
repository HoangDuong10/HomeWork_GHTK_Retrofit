package com.example.homework_ghtk_retrofit.repository

import com.example.homework_ghtk_retrofit.model.PokemonResponse
import com.example.homework_ghtk_retrofit.retrofit.ApiService

interface PokemonRepository {
    suspend fun getData(limit: Int, offset: Int) : Result<PokemonResponse>
}