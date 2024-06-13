package com.example.musicapp.data

import com.example.musicapp.network.SongService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val songRepository:SongRepository
}
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://shidou134.000webhostapp.com/server/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(Json{ignoreUnknownKeys=true}.asConverterFactory("application/json".toMediaType()))
        .build()
    private val retrofitService: SongService by lazy {
        retrofit.create(SongService::class.java)
    }
    override val songRepository: SongRepository by lazy {
        NetworkSongRepository(retrofitService)
    }
}
