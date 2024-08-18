package com.example.homework_ghtk_retrofit.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework_ghtk_retrofit.databinding.ItemLoadingBinding
import com.example.homework_ghtk_retrofit.databinding.ItemPokemonBinding
import com.example.homework_ghtk_retrofit.model.Pokemon

class PokemonAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_POKEMON: Int = 1
        const val TYPE_LOADING: Int = 2
    }
    private var isLoadingAdd: Boolean = false
    class PokemonViewHolder(val binding : ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)
    class LoadingViewHolder( loadingBinding: ItemLoadingBinding) : RecyclerView.ViewHolder(loadingBinding.root)
    var listData:MutableList<Pokemon> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LOADING -> {
                val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingViewHolder(binding)
            }
            else -> {
                val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PokemonViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PokemonViewHolder){
            listData[position].url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${position+1}.png"
            holder.binding.tvName.text = listData[position].name
            if(listData[position].name.isNotEmpty()){
                Glide.with(holder.binding.ivPokemon.context).load(listData[position].url).into(holder.binding.ivPokemon)
                holder.binding.ivPokemon.visibility = View.VISIBLE
            }else{
                holder.binding.ivPokemon.visibility = View.GONE
            }
//

        }
    }

    override fun getItemViewType(position: Int): Int {
        if(position == listData.size -1 && isLoadingAdd){
            return TYPE_LOADING
        }
        return TYPE_POKEMON
    }


    override fun getItemCount(): Int {
        return listData.size
    }

    fun addListPokemon(data: List<Pokemon>) {
        val positionStart=listData.size
        listData.addAll(data)
        notifyItemRangeInserted(positionStart, data.size)

    }

    fun addFooterLoading() {
        isLoadingAdd = true
        listData.add(Pokemon())
        notifyItemInserted(listData.lastIndex)
    }

    fun remoteFooterLoading() {
        if(listData.isNotEmpty()){
            isLoadingAdd = false
            val position =listData.size-1
            listData.removeAt(position)
            notifyItemRemoved(position)
        }


    }
    fun clearListPokemon() {
        val itemCount = listData.size
        listData.clear()
        notifyItemRangeRemoved(0, itemCount)
    }

}