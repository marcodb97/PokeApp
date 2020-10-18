package com.marcodallaba.pokeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.marcodallaba.pokeapp.R
import com.marcodallaba.pokeapp.databinding.PokemonBottomSheetDialogBinding
import com.marcodallaba.pokeapp.model.PokemonDetail
import java.util.*

class PokemonBottomSheetDialogFragment(
    private val pokemonDetail: PokemonDetail
) : BottomSheetDialogFragment() {

    private lateinit var binding: PokemonBottomSheetDialogBinding

    override fun getTheme(): Int = R.style.PokemonBottomSheetDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.pokemon_bottom_sheet_dialog,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this).load(pokemonDetail.frontPic).into(binding.imageView)
        binding.nameTextView.text = pokemonDetail.name.capitalize(Locale.ROOT)
        binding.typeTextView.text = pokemonDetail.getTypesList()
        binding.hpTextView.text = getString(R.string.hp, pokemonDetail.hp.toString())
        binding.attackTextView.text = getString(R.string.attack, pokemonDetail.attack.toString())
        binding.defenseTextView.text = getString(R.string.defense, pokemonDetail.defense.toString())
        binding.specialAttackTextView.text =
            getString(R.string.special_attack, pokemonDetail.specialAttack.toString())
        binding.specialDefenseTextView.text =
            getString(R.string.special_defense, pokemonDetail.specialDefense.toString())
        binding.speedTextView.text = getString(R.string.speed, pokemonDetail.speed.toString())
    }
}