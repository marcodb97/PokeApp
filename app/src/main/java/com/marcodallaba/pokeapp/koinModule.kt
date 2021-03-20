package com.marcodallaba.pokeapp

import com.marcodallaba.pokeapp.PokeApp.Companion.BASE_URL
import com.marcodallaba.pokeapp.api.PokeService
import com.marcodallaba.pokeapp.data.PokemonRepository
import com.marcodallaba.pokeapp.db.PokemonDatabase
import com.marcodallaba.pokeapp.viewmodels.PokemonViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@OptIn(KoinApiExtension::class)
@ExperimentalCoroutinesApi
val appModule = module {

    single {
        PokemonDatabase.getInstance(androidContext())
    }

    single {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        logger
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single {
        get<Retrofit>().create(PokeService::class.java)
    }

    single {
        PokemonRepository(
            get(),
            get()
        )
    }

    viewModel {
        PokemonViewModel(
            get()
        )
    }
}