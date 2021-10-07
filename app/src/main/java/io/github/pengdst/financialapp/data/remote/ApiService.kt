package io.github.pengdst.financialapp.data.remote

import io.github.pengdst.financialapp.data.remote.model.UserDto
import io.github.pengdst.financialapp.data.remote.request.CreateNewUserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("user")
    suspend fun getAllUser(): Response<List<UserDto>>

    @GET("user/{user_id}")
    suspend fun getDetailUser(
        @Path("user_id") userId: Int
    ): Response<UserDto>

    @POST("user")
    suspend fun createNewUser(
        @Body request: CreateNewUserRequest
    ): Response<List<UserDto>>

}