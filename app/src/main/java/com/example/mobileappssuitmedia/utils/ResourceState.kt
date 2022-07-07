package com.example.mobileappssuitmedia.utils

sealed class ResourceState<T>(val data: T?, val message: String? = null) {
    class Success<T>(data: T?, message: String?) : ResourceState<T>(data, message)
    class Error<T>(data: T?, message: String?) : ResourceState<T>(data, message)
}
