package com.dhruv.core.common.utils

sealed class ServerResponse<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ServerResponse<T>(data)
    class Error<T>(message: String, data: T? = null) : ServerResponse<T>(data, message)
    class Loading<T>(data: T? = null) : ServerResponse<T>(data)
}