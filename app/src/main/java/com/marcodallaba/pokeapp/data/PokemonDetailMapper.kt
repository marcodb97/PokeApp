package com.marcodallaba.pokeapp.data

import com.marcodallaba.pokeapp.model.PokemonDetail
import com.marcodallaba.pokeapp.model.PokemonDetailResponse

object PokemonDetailMapper {

    @JvmStatic
    fun map(pokemonDetailResponse: PokemonDetailResponse): PokemonDetail {
        return PokemonDetail(
            id = pokemonDetailResponse.id,
            name = pokemonDetailResponse.name,
            type = pokemonDetailResponse.types[0].type.name,
            frontPic = pokemonDetailResponse.sprites.front_default,
            backPic = pokemonDetailResponse.sprites.back_default,
            hp = pokemonDetailResponse.stats[0].base_stat,
            attack = pokemonDetailResponse.stats[1].base_stat,
            defense = pokemonDetailResponse.stats[2].base_stat,
            specialAttack = pokemonDetailResponse.stats[3].base_stat,
            specialDefense = pokemonDetailResponse.stats[4].base_stat,
            speed = pokemonDetailResponse.stats[5].base_stat
        )
    }
}