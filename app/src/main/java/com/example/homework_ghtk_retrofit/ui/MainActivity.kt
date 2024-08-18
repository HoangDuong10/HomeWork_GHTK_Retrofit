package com.example.homework_ghtk_retrofit.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examplerecyclerview.widget.PaginationScrollListener
import com.example.homework_ghtk_retrofit.R
import com.example.homework_ghtk_retrofit.databinding.ActivityMainBinding
import com.example.homework_ghtk_retrofit.viewmodel.PokemonViewModel


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PokemonViewModel by viewModels()
    private lateinit var adapterPokemon: PokemonAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel.getData()
        linearLayoutManager = GridLayoutManager(this, 2)
        adapterPokemon = PokemonAdapter()
        binding.rcvPokemon.apply {
            layoutManager = linearLayoutManager
            adapter = adapterPokemon
        }

        viewModel.results.observe(this@MainActivity) { pokemonList ->
            adapterPokemon.remoteFooterLoading()
            adapterPokemon.addListPokemon(pokemonList)
        }
        viewModel.isTest.observe(this@MainActivity) {
            adapterPokemon.remoteFooterLoading()
        }
        loadMore()
    }

    private fun loadMore() {
        binding.rcvPokemon.addOnScrollListener(object :
            PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItem() {
                adapterPokemon.addFooterLoading()
                isLoading = true
                loadNextPage()
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

        })
    }

    private fun loadNextPage() {
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getData()
            isLoading = false
        }, 2000)
    }


}