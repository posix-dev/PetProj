package me.uptop.network.dto.common

data class ResponseError(
    val code: String,
    val level: String,
    val message: String,
    val link: String? = null,
    val params: List<KeyValue>? = null
)
