package com.example.musicapp.ui.mymusic.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class MyMusicViewModel:ViewModel() {
    val auth = FirebaseAuth.getInstance()

    fun logout() {
        auth.signOut()
    }
}