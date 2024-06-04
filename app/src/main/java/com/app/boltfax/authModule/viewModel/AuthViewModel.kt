package com.app.boltfax.authModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.boltfax.authModule.repository.AuthRepository
import com.app.boltfax.base.Resource
import com.app.boltfax.base.UserDataModel
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    val showLoader: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val loginObserver: MutableLiveData<Resource<UserDataModel>> by lazy {
        MutableLiveData<Resource<UserDataModel>>()
    }

    fun login(contact: String) {
        synchronized(loginObserver) {
            showLoader.postValue(true)
        }
        viewModelScope.launch {
            authRepository.login(contact).let {

                showLoader.postValue(false)
                loginObserver.postValue(it)
            }
        }

    }

}