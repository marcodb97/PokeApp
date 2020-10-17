package com.marcodallaba.pokeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.marcodallaba.pokeapp.Injection
import com.marcodallaba.pokeapp.R
import com.marcodallaba.pokeapp.databinding.ActivityMainBinding
import com.marcodallaba.pokeapp.viewmodels.SearchPokemonViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchPokemonViewModel: SearchPokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // get the view model
        searchPokemonViewModel = ViewModelProvider(this, Injection.provideViewModelFactory(this))
            .get(SearchPokemonViewModel::class.java)

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)
    }
}