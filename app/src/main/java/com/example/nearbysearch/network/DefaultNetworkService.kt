package com.example.nearbysearch.network

import android.util.Log
import com.example.nearbysearch.network.model.search_result.SearchResultDto
import com.example.nearbysearch.util.Constants.Companion.API_KEY
import com.example.nearbysearch.util.Constants.Companion.BASE_URL
import com.example.nearbysearch.util.Constants.Companion.TIME_OUT
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


class DefaultNetworkService : NetworkService {
    override suspend fun getNearbySpots(lon: Double, lat: Double, radius: Int) : SearchResultDto {
                return ktorHttpClient.get("${BASE_URL}lat=$lat&lon=$lon&radius=$radius&view=Unified&relatedPois=off&key=$API_KEY")
                    .body()
    }
}

private val ktorHttpClient = HttpClient(Android){
    install(ContentNegotiation){
        json(
            Json{
                prettyPrint = true
                ignoreUnknownKeys = true
                isLenient = true
            }
        )
        engine {
            connectTimeout = TIME_OUT
            socketTimeout = TIME_OUT
        }
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.v("AppDebug, Logger Ktor -->", message)
            }
        }
        level = LogLevel.ALL
    }
    install(ResponseObserver) {
        onResponse { res ->
            Log.d("AppDebug, HTTP status: -->",res.status.value.toString())
        }
    }
    install(DefaultRequest){
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
}