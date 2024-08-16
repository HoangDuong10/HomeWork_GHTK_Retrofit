package com.example.homework_ghtk_retrofit.retrofit

import com.example.homework_ghtk_retrofit.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/v2/pokemon")
    suspend fun getData( @Query("offset") offset : Int,@Query("limit") page : Int) : Response<PokemonResponse>
}