package com.marcodallaba.pokeapp

import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
@KoinApiExtension
class PokeApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        // start Koin DI module
        startKoin {
            androidLogger()
            androidContext(this@PokeApp)
            modules(appModule)
        }
    }

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }


}
