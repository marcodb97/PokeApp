package com.marcodallaba.pokeapp.ui.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.marcodallaba.pokeapp.R
import com.marcodallaba.pokeapp.databinding.PokemonViewItemBinding
import com.marcodallaba.pokeapp.model.PokemonBase
import com.marcodallaba.pokeapp.ui.adapters.PokemonAdapter

/**
 * View Holder for a [PokemonBase] RecyclerView list item.
 */
class PokemonViewHolder(
    private val binding: PokemonViewItemBinding,
    onPokemonClickListener: PokemonAdapter.OnPokemonClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    private var pokemon: PokemonBase? = null

    init {
        binding.root.setOnClickListener {
            pokemon?.let {
                onPokemonClickListener.onClick(pokemonName = it.name)
            }
        }
    }

    fun bind(pokemon: PokemonBase?) {
        if (pokemon == null) {
            val resources = itemView.resources
            binding.pokemonName.text = resources.getString(R.string.loading)
        } else {
            showPokemonData(pokemon)
        }
    }

    private fun showPokemonData(pokemon: PokemonBase) {
        this.pokemon = pokemon
        binding.pokemonName.text = "${pokemon.id} ${pokemon.name}"
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onPokemonClickListener: PokemonAdapter.OnPokemonClickListener
        ): PokemonViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: PokemonViewItemBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.pokemon_view_item, parent, false)
            return PokemonViewHolder(binding, onPokemonClickListener)
        }
    }
}
