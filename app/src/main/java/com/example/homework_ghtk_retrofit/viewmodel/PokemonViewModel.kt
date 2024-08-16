package com.example.homework_ghtk_retrofit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_ghtk_retrofit.model.Pokemon
import com.example.homework_ghtk_retrofit.model.PokemonResponse
import com.example.homework_ghtk_retrofit.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val repo : PokemonRepository) : ViewModel() {
    val limit = 20
    var offset = 0
    private val _results = MutableStateFlow<List<Pokemon>>(emptyList())
    val results: StateFlow<List<Pokemon>> get() = _results

    fun getData(){
        viewModelScope.launch {
            val result = repo.getData(limit, offset)
            result.onSuccess { data ->
                if(data.results.isNotEmpty()){
                    offset+=20
                }
                _results.value = data.results


            }.onFailure { error ->


            }
        }
    }
    fun onRefresh(){
        offset = 0
        getData()

    }

    fun loadMore(){
        getData()
    }
}