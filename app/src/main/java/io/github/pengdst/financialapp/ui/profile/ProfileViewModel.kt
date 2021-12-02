package io.github.pengdst.financialapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.pengdst.financialapp.domain.model.User

class ProfileViewModel() : ViewModel() {

    private val _userLivedata = MutableLiveData<User>()
    val userLiveData: LiveData<User> = _userLivedata

    private val _errorLivedata = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLivedata

    fun loadUserProfile(userId: Int) {
        try {
            _userLivedata.postValue(
                User(
                    email = "donald@crispychicken.com",
                    id = userId,
                    imageUrl = "https://d.newsweek.com/en/full/1061787/donald-trump-approval-rating.jpg",
                    name = "Donald"
                )
            )
        } catch (e: Exception) {
            _errorLivedata.postValue("Gagal Mengambil Data!")
        }
    }

}