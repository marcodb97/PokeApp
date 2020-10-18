package com.marcodallaba.pokeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.marcodallaba.pokeapp.Injection
import com.marcodallaba.pokeapp.R
import com.marcodallaba.pokeapp.data.PokemonRepository
import com.marcodallaba.pokeapp.databinding.ActivityMainBinding
import com.marcodallaba.pokeapp.ui.adapters.PokemonAdapter
import com.marcodallaba.pokeapp.ui.adapters.PokemonLoadStateAdapter
import com.marcodallaba.pokeapp.utils.setSafeOnClickListener
import com.marcodallaba.pokeapp.viewmodels.PokemonViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class PokemonListActivity : AppCompatActivity(), PokemonAdapter.OnPokemonClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pokemonViewModel: PokemonViewModel
    private val adapter = PokemonAdapter(this)

    private var loadJob: Job? = null
    private var searchJob: Job? = null

    private fun loadPokemon() {
        // Make sure we cancel the previous job before creating a new one
        loadJob?.cancel()
        loadJob = lifecycleScope.launch {
            pokemonViewModel.loadPokemon().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // get the view model
        pokemonViewModel = ViewModelProvider(this, Injection.provideViewModelFactory(this))
            .get(PokemonViewModel::class.java)

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)

        initAdapter()

        loadPokemon()

        binding.retryButton.setSafeOnClickListener { adapter.retry() }
    }

    private fun initAdapter() {
        binding.list.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PokemonLoadStateAdapter { adapter.retry() },
            footer = PokemonLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onClick(pokemonName: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            val pokemonDetail = pokemonViewModel.getPokemonDetail(pokemonName)
            if (pokemonDetail is PokemonRepository.PokemonDetailResult.Success) {
                val pokemonBottomSheetDialogFragment = PokemonBottomSheetDialogFragment(pokemonDetail = pokemonDetail.result)
                pokemonBottomSheetDialogFragment.show(
                    supportFragmentManager,
                    PokemonBottomSheetDialogFragment::class.java.canonicalName
                )
            } else if (pokemonDetail is PokemonRepository.PokemonDetailResult.Error){
                Toast.makeText(this@PokemonListActivity, "\uD83D\uDE28 Wooops ${pokemonDetail.throwable}", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private val TAG = PokemonListActivity::class.java.simpleName
    }
}