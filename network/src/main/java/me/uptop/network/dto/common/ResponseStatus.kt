package me.uptop.network.dto.common

data class ResponseStatus(
    val code: Int = 200,
    val message: String = "OK",
    val description: String? = null
)
