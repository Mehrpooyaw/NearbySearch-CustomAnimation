package com.example.nearbysearch

import android.app.Application
import com.example.nearbysearch.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(
                networkModule,
                datasourceModule,
                viewModelModule,
                cacheModule,
                repositoryModule
            )
        }
    }
}