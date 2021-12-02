package io.github.pengdst.financialapp.ui.profile

import io.github.pengdst.financialapp.domain.model.User

class ProfilePresenter(private val view: ProfileView) {

    fun loadUserProfile(userId: Int) {
        try {
            view.showUserProfile(
                User(
                    email = "donald@crispychicken.com",
                    id = userId,
                    imageUrl = "https://d.newsweek.com/en/full/1061787/donald-trump-approval-rating.jpg",
                    name = "Donald"
                )
            )
        } catch (e: Exception) {
            view.failedLoadUserProfile("Gagal Mengambil Data!")
        }
    }

}