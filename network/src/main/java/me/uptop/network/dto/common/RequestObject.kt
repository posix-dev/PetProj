package me.uptop.network.dto.common

data class RequestObject<T>(
    val params: Params? = null,
    var data: T
)