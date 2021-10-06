package io.github.pengdst.financialapp.ui.profile

import io.github.pengdst.financialapp.domain.model.User

interface ProfileView {

    fun showUserProfile(user: User)
    fun failedLoadUserProfile(message: String)

}