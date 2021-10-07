package io.github.pengdst.financialapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.pengdst.financialapp.data.repository.BaseRepository
import io.github.pengdst.financialapp.data.repository.UserRepository
import io.github.pengdst.financialapp.ui.profile.ProfileViewModel

class ViewModelFactory(private val repository: BaseRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository as UserRepository)
            }
            else -> return super.create(modelClass)
        } as T
    }
}