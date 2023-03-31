package com.deepak.myapplication.infra

sealed class AppRequest {
    data class Result<T>(val result: T) : AppRequest()

    data class ListResult<T>(val result: List<T>) : AppRequest()

    data class Error(val error: Throwable) : AppRequest()
    data class Loading(val message: String = ""): AppRequest()
}