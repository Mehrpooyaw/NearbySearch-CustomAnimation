package com.example.nearbysearch.di

import com.example.nearbysearch.Database
import com.example.nearbysearch.cache.SqldelghtAdaptersImpl
import com.example.nearbysearch.datasource.local.DefaultNearbyLocalSource
import com.example.nearbysearch.datasource.local.NearbyLocalSource
import com.example.nearbysearch.datasource.remote.DefaultNearbyRemoteSource
import com.example.nearbysearch.datasource.remote.NearbyRemoteSource
import com.example.nearbysearch.network.DefaultNetworkService
import com.example.nearbysearch.network.NetworkService
import com.example.nearbysearch.presentation.ui.favorites.FavoritesViewModel
import com.example.nearbysearch.presentation.ui.search.SearchViewModel
import com.example.nearbysearch.presentation.ui.map.MapViewModel
import com.example.nearbysearch.presentation.ui.settings.SettingsViewModel
import com.example.nearbysearch.repository.NearbySearchRepository
import com.example.sqldelight.nearbysearch.SpotDao
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.module

fun Scope.createDriver(databaseName : String) : SqlDriver = AndroidSqliteDriver(Database.Schema,androidContext(), databaseName)

val networkModule = module {
    factory<NetworkService> {
        DefaultNetworkService()
    }
}

val cacheModule = module {
    single {
        Database(createDriver("NearbySearch"), SpotDaoAdapter = SpotDao.Adapter(
            addressAdapter = SqldelghtAdaptersImpl.addressConverter,
            entryPointsAdapter =SqldelghtAdaptersImpl.entryPointsConverter,
            poiAdapter = SqldelghtAdaptersImpl.poiConverter,
            viewportAdapter = SqldelghtAdaptersImpl.viewPortConverter
        )
        )
    }
}


val datasourceModule = module {
    factory<NearbyRemoteSource> {
        DefaultNearbyRemoteSource(get())
    }
    factory<NearbyLocalSource> {
        DefaultNearbyLocalSource(get())
    }
}

val repositoryModule = module {
    single {
        NearbySearchRepository()
    }
}

val viewModelModule = module {
    viewModel {
        SearchViewModel(get())
    }
    viewModel {
        MapViewModel(get())
    }
    viewModel {
        FavoritesViewModel(get())
    }
    viewModel {
        SettingsViewModel(get())
    }
}