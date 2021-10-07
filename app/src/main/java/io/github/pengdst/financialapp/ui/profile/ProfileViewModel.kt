package io.github.pengdst.financialapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.pengdst.financialapp.data.repository.UserRepository
import io.github.pengdst.financialapp.data.vo.ResultResource
import io.github.pengdst.financialapp.domain.model.User
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userProfile = MutableLiveData<ResultResource<User?>>()
    val userProfile: LiveData<ResultResource<User?>> get() = _userProfile

    fun loadUserProfile(userId: Int) {
        viewModelScope.launch {
            _userProfile.postValue(userRepository.loadUserProfile(userId))
        }
    }

}