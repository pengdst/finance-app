package io.github.pengdst.financialapp.data.repository

import io.github.pengdst.financialapp.data.remote.ApiService
import io.github.pengdst.financialapp.data.remote.model.UserDto
import io.github.pengdst.financialapp.data.vo.ResultReource
import io.github.pengdst.financialapp.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val apiService: ApiService) {

    suspend fun loadUserProfile(userId: Int): ResultReource<User?> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getDetailUser(userId)
            if (response.isSuccessful) {
                val user = response.body()?.let { dtoToUser(it) }
                ResultReource.Success(
                    data = user
                )
            }else {
                ResultReource.Error()
            }
        }catch (e: Exception) {
            ResultReource.Error(message = e.localizedMessage)
        }
    }

    private fun dtoToUser(dto: UserDto) = User(
        id = dto.id ?: 0,
        name = dto.name ?: "",
        email = dto.email ?: "",
        imageUrl = dto.imageUrl ?: ""
    )

}