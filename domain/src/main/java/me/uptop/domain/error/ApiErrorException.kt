package me.uptop.domain.error

data class ApiErrorException(
    val code: String? = null,
    val level: String? = null,
    val errorMessage: String? = null
) : Exception(
    "code: $code; message: $errorMessage"
)