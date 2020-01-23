package me.uptop.network.dto.common

data class ResponseObject<T>(
    val status: ResponseStatus = ResponseStatus(),
    val errors: List<ResponseError>? = null,
    val data: T? = null
)
