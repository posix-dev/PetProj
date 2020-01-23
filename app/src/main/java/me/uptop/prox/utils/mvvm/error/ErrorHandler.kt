package me.uptop.prox.utils.mvvm.error

interface ErrorHandler : (Throwable, Retrier) -> Unit