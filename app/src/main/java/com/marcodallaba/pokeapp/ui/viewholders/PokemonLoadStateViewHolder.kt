package com.marcodallaba.pokeapp.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.marcodallaba.pokeapp.databinding.PokemonLoadStateFooterViewItemBinding

class PokemonLoadStateViewHolder(
    private val binding: PokemonLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): PokemonLoadStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = PokemonLoadStateFooterViewItemBinding.inflate(layoutInflater, parent, false)
            return PokemonLoadStateViewHolder(binding, retry)
        }
    }
}
