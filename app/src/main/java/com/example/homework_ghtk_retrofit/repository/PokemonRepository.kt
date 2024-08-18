package com.example.homework_ghtk_retrofit.repository

import com.example.homework_ghtk_retrofit.model.PokemonResponse

interface PokemonRepository {
    suspend fun getData(limit: Int, offset: Int) : Result<PokemonResponse>
}