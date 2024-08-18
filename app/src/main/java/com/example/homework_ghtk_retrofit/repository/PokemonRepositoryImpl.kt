package com.example.homework_ghtk_retrofit.repository

import com.example.homework_ghtk_retrofit.model.PokemonResponse
import com.example.homework_ghtk_retrofit.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(private val apiService: ApiService): PokemonRepository {
    override suspend fun getData(limit: Int, offset: Int): Result<PokemonResponse> {
        return withContext(Dispatchers.IO) {
            try {
                // Gọi API thông qua ApiService trên luồng IO
                val response = apiService.getData(offset,limit)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        Result.success(data)
                    } else {
                        Result.failure(Exception("No data available"))
                    }
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}