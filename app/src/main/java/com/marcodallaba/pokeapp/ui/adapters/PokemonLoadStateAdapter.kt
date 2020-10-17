package com.marcodallaba.pokeapp.ui.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.marcodallaba.pokeapp.ui.viewholders.PokemonLoadStateViewHolder

class PokemonLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PokemonLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: PokemonLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): PokemonLoadStateViewHolder {
        return PokemonLoadStateViewHolder.create(parent, retry)
    }
}
