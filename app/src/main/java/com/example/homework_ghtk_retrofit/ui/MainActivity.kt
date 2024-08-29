package com.example.homework_ghtk_retrofit.ui

import DataStoreManager
import NetworkUtils
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var dataStoreManager: DataStoreManager
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
        dataStoreManager = DataStoreManager(this)
        viewModel.getData()
        linearLayoutManager = GridLayoutManager(this, 2)
        adapterPokemon = PokemonAdapter()
        binding.rcvPokemon.apply {
            layoutManager = linearLayoutManager
            adapter = adapterPokemon
        }

        if (NetworkUtils.isInternetAvailable(this)) {
            viewModel.results.observe(this@MainActivity) { pokemonList ->
                adapterPokemon.remoteFooterLoading()
                adapterPokemon.addListPokemon(pokemonList)

                dataStoreManager.savePokemonList(adapterPokemon.listData)
                Log.d("tag", "${pokemonList.size}")

            }
            viewModel.isTest.observe(this@MainActivity) {
                adapterPokemon.remoteFooterLoading()
            }
            loadMore()

        } else {
            adapterPokemon.addListPokemon(dataStoreManager.getPokemonList())
        }
        Log.d("tag", "${dataStoreManager.getPokemonList().size}")
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