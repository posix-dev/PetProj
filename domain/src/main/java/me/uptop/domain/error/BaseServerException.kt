package me.uptop.domain.error

data class BaseServerException(
    val code: Int,
    private val msg: String? = null,
    private val desc: String? = null,
    private val traceId: String? = null,
    private val requestInfo: String? = null
) : Exception("code: $code; message: $msg; trace-id: $traceId; request: $requestInfo")