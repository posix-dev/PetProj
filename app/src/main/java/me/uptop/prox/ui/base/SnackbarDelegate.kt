package me.uptop.prox.ui.base

import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

interface SnackbarDelegate {

    fun systemMessage(@StringRes message: Int, sticky: Boolean = true, duration: Int = Snackbar.LENGTH_SHORT)

    fun actionSystemMessage(
        @StringRes message: Int,
        @StringRes actionMessage: Int,
        sticky: Boolean = false,
        duration: Int = Snackbar.LENGTH_INDEFINITE,
        action: () -> Unit
    )

    fun retriableSystemMessage(
        @StringRes message: Int,
        sticky: Boolean = false,
        duration: Int = Snackbar.LENGTH_INDEFINITE,
        retry: () -> Unit
    )

    fun retriableSystemMessage(
        message: String,
        sticky: Boolean = false,
        duration: Int = Snackbar.LENGTH_INDEFINITE,
        retry: () -> Unit
    )
}