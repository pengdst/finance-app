package io.github.pengdst.financialapp.data.repository

import io.github.pengdst.financialapp.data.remote.ApiService
import io.github.pengdst.financialapp.data.remote.model.UserDto
import io.github.pengdst.financialapp.data.vo.ResultResource
import io.github.pengdst.financialapp.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val apiService: ApiService) : BaseRepository() {

    suspend fun loadUserProfile(userId: Int): ResultResource<User?> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getDetailUser(userId)
            if (response.isSuccessful) {
                val user = response.body()?.let { dtoToUser(it) }
                ResultResource.Success(
                    data = user
                )
            }else {
                ResultResource.Error()
            }
        }catch (e: Exception) {
            ResultResource.Error(message = e.localizedMessage)
        }
    }

    private fun dtoToUser(dto: UserDto) = User(
        id = dto.id ?: 0,
        name = dto.name ?: "",
        email = dto.email ?: "",
        imageUrl = dto.imageUrl ?: ""
    )

}