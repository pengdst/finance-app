package io.github.pengdst.financialapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var instance: Retrofit? = null

    fun build() = instance ?: Retrofit.Builder()
        .baseUrl("https://private-f10e93-financeapp5.apiary-mock.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().also {
            instance = it
        }
}