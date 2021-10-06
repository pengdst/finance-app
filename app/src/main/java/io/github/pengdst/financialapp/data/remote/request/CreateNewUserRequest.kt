package io.github.pengdst.financialapp.data.remote.request


import com.google.gson.annotations.SerializedName

data class CreateNewUserRequest(
    @SerializedName("email")
    var email: String?,
    @SerializedName("image_url")
    var imageUrl: String?,
    @SerializedName("name")
    var name: String?
)