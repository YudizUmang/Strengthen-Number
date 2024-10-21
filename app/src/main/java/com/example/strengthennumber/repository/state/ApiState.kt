package com.example.strengthennumber.repository.state

sealed class ApiState<T> {
    data object Loading : ApiState<Nothing>()
    data class Success<T>(val data: T) : ApiState<T>()
    data class Error(val message: String) : ApiState<String>()
}