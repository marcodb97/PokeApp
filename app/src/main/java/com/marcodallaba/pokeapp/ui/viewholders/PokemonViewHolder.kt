package com.marcodallaba.pokeapp.ui.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marcodallaba.pokeapp.R
import com.marcodallaba.pokeapp.model.PokemonBase

/**
 * View Holder for a [PokemonBase] RecyclerView list item.
 */
class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.pokemon_name)

    private var pokemon: PokemonBase? = null

    init {
        view.setOnClickListener {
            //TODO
        }
    }

    fun bind(pokemon: PokemonBase?) {
        if (pokemon == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
        } else {
            showPokemonData(pokemon)
        }
    }

    private fun showPokemonData(pokemon: PokemonBase) {
        this.pokemon = pokemon
        name.text = "${pokemon.id} ${pokemon.name}"
    }

    companion object {
        fun create(parent: ViewGroup): PokemonViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.pokemon_view_item, parent, false)
            return PokemonViewHolder(view)
        }
    }
}
