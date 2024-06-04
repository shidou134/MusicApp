package com.example.musicapp

import android.app.Application
import com.example.musicapp.data.AppContainer
import com.example.musicapp.data.DefaultAppContainer

class SongApplication: Application() {
    lateinit var container : AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}