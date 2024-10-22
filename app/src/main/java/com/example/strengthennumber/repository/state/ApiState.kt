package com.example.strengthennumber.repository.state

sealed class ApiState<out T> {
    data object Loading : ApiState<Nothing>()
    data class Success<T>(val data: T) : ApiState<T>()
    data class Error<T>(val message: String?) : ApiState<T>()
}