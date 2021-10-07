package io.github.pengdst.financialapp.data.vo

sealed class ResultResource<Data> {
    data class Success<Data>(
        val data: Data,
        val message: String = "Success Get Data."
    ) : ResultResource<Data>()

    data class Error<Data>(
        val data: Data? = null,
        val message: String = "Unknown Error!"
    ) : ResultResource<Data>()
}
