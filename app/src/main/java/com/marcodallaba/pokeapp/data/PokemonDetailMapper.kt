package com.marcodallaba.pokeapp.data

import com.marcodallaba.pokeapp.model.PokemonDetail
import com.marcodallaba.pokeapp.model.PokemonDetailResponse

object PokemonDetailMapper {

    @JvmStatic
    fun map(pokemonDetailResponse: PokemonDetailResponse): PokemonDetail {

        val types = pokemonDetailResponse.types.map {
            it.type.name
        }

        return PokemonDetail(
            id = pokemonDetailResponse.id,
            name = pokemonDetailResponse.name,
            types = types,
            frontPic = pokemonDetailResponse.sprites.front_default,
            hp = pokemonDetailResponse.stats[0].base_stat,
            attack = pokemonDetailResponse.stats[1].base_stat,
            defense = pokemonDetailResponse.stats[2].base_stat,
            specialAttack = pokemonDetailResponse.stats[3].base_stat,
            specialDefense = pokemonDetailResponse.stats[4].base_stat,
            speed = pokemonDetailResponse.stats[5].base_stat
        )
    }
}