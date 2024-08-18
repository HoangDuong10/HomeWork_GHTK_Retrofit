package com.example.homework_ghtk_retrofit.model

import javax.inject.Inject

data class PokemonResponse (
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Pokemon>
)