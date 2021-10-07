package io.github.pengdst.financialapp.data.vo

sealed class Result<Data> {
    data class Success<Data>(val data: Data, val message: String = "Success Get Data."): Result<Data>()
    data class Error<Data>(val data: Data? = null, val message: String = "Unknown Error!"): Result<Data>()
}
