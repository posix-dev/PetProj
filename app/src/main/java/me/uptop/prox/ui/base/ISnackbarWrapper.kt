package me.uptop.prox.ui.base

interface ISnackbarWrapper {
    fun wrapWithSnackBarDelegate(block: SnackbarDelegate.() -> Unit)
}