package com.marcodallaba.pokeapp.ui.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.marcodallaba.pokeapp.model.PokemonBase
import com.marcodallaba.pokeapp.ui.viewholders.PokemonViewHolder

/**
 * Adapter for the list of pokemon.
 */
class PokemonAdapter : PagingDataAdapter<PokemonBase, PokemonViewHolder>(UI_MODEL_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon)
    }

    companion object {
        private val UI_MODEL_COMPARATOR = object : DiffUtil.ItemCallback<PokemonBase>() {
            override fun areItemsTheSame(oldItem: PokemonBase, newItem: PokemonBase): Boolean {
                return (oldItem.id == newItem.id &&
                        oldItem.name == newItem.name &&
                        oldItem.url == newItem.url)
            }

            override fun areContentsTheSame(oldItem: PokemonBase, newItem: PokemonBase): Boolean =
                oldItem == newItem
        }
    }
}