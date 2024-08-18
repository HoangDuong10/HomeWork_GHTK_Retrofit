package com.example.homework_ghtk_retrofit.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_ghtk_retrofit.model.Pokemon
import com.example.homework_ghtk_retrofit.model.PokemonResponse
import com.example.homework_ghtk_retrofit.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(val repo: PokemonRepository) : ViewModel() {
    val limit = 20
    var offset = 0
    private val _results = MutableLiveData<List<Pokemon>>()
    val results: MutableLiveData<List<Pokemon>> get() = _results

    private val _isTest = MutableLiveData<Boolean>()
    val isTest: MutableLiveData<Boolean> get() = _isTest


    fun getData() {
        viewModelScope.launch {
            val result = repo.getData(limit, offset)
            result.onSuccess { data ->
                val polemon = data.results
                _results.value = polemon
                if (data.results.isEmpty()) {
                    _isTest.postValue(true)
                }
            }.onFailure { error ->
                Log.d("TAG", "${error.message}")
            }
            offset += 20
        }
    }


}