package io.github.pengdst.financialapp.ui.profile

import android.util.Log
import io.github.pengdst.financialapp.data.remote.ApiService
import io.github.pengdst.financialapp.data.remote.model.UserDto
import io.github.pengdst.financialapp.domain.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilePresenter(private val view: ProfileView, private val apiService: ApiService) {

    fun loadUserProfile(userId: Int) {
        apiService.getDetailUser(userId).enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                if (response.isSuccessful) {
                    response.body()?.let { dto ->
                        view.showUserProfile(dtoToUser(dto))
                    }
                }
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                view.failedLoadUserProfile("Gagal Mengambil Data!")
                Log.e("ProfileFragment", "Failure: ", t)
            }

        })
    }

    private fun dtoToUser(dto: UserDto) = User(
        id = dto.id ?: 0,
        name = dto.name ?: "",
        email = dto.email ?: "",
        imageUrl = dto.imageUrl ?: ""
    )

}