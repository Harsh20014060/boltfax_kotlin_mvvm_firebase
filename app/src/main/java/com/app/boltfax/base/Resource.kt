package com.app.boltfax.base

import java.io.Serializable


enum class ApiStatus {
    HTTPS_SUCCESS,
    HTTPS_ERROR,
    DATA_NOT_FOUND
}

sealed class Resource<out T> private constructor(
    val apiStatus: ApiStatus,
    val data: T? = null,
    val message: String? = null
) : Serializable {

    class Success<T>(data: T?, message: String? = null) :
        Resource<T>(ApiStatus.HTTPS_SUCCESS, data, message = message)

    class Error<T>(message: String?) :
        Resource<T>(
            ApiStatus.HTTPS_ERROR,
            message = message
        )

    class DataNotFound<T>(message: String? = "No data found") :
        Resource<T>(ApiStatus.DATA_NOT_FOUND, message = message)


}