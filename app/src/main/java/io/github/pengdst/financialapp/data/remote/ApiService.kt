package io.github.pengdst.financialapp.data.remote

import io.github.pengdst.financialapp.data.remote.request.CreateNewUserRequest
import io.github.pengdst.financialapp.data.remote.model.UserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("user")
    fun getAllUser(): Call<List<UserDto>>

    @GET("user/{user_id}")
    fun getDetailUser(
        @Path("user_id") userId: Int
    ): Call<UserDto>

    @POST("user")
    fun createNewUser(
        @Body request: CreateNewUserRequest
    ): Call<List<UserDto>>

}