package com.example.musicapp.ui

import androidx.annotation.StringRes
import com.example.musicapp.R

data class ErrorState(
    val hasError: Boolean = false,
    @StringRes val errorMessageStringResource: Int = R.string.empty_string
)