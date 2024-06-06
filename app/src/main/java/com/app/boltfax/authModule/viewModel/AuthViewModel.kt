package com.app.boltfax.authModule.viewModel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.boltfax.authModule.repository.AuthRepository
import com.app.boltfax.base.Resource
import com.app.boltfax.base.UserDataModel
import com.app.boltfax.util.PasswordUtil
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    val showLoader: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val loginObserver: MutableLiveData<Resource<UserDataModel>> by lazy {
        MutableLiveData<Resource<UserDataModel>>()
    }

    fun login(contact: String, password: String) {
        synchronized(loginObserver) {
            showLoader.postValue(true)
        }
        viewModelScope.launch {
            authRepository.login(contact).let {

                when (it) {

                    is Resource.Success -> {
                        if (PasswordUtil.decrypt(
                                PasswordUtil.generateSaltedKey(
                                    name = it.data?.fullName ?: "",
                                    documentName = it.data?.documentName ?: ""
                                ), encryptedPass = it.data?.password ?: ""
                            ) == password
                        ) {
                            loginObserver.postValue(
                                Resource.Success(
                                    data = it.data,
                                    message = "Login Successful"
                                )
                            )
                        } else {
                            loginObserver.postValue(Resource.Error(message = "Invalid Credentials"))
                        }
                    }

                    else -> {
                        loginObserver.postValue(it)
                    }
                }

                showLoader.postValue(false)

            }
        }

    }

    val otpObserver: MutableLiveData<Resource<String>> by lazy {
        MutableLiveData<Resource<String>>()
    }

    fun generateOTP(
        activity: Activity,
        contact: String,

        ) {
        synchronized(otpObserver) {
            showLoader.postValue(true)
        }
        viewModelScope.launch {
            authRepository.generateOTP(activity, contact).let {

                showLoader.postValue(false)
                otpObserver.postValue(it)
            }
        }

    }

    val verifyOTPObserver: MutableLiveData<Resource<Any>> by lazy {
        MutableLiveData<Resource<Any>>()
    }

    fun verifyOTP(otpID: String, otp: String) {
        synchronized(verifyOTPObserver) {
            showLoader.postValue(true)
        }
        viewModelScope.launch {
            authRepository.verifyOTP(otpID, otp).let {

                showLoader.postValue(false)
                verifyOTPObserver.postValue(it)
            }
        }

    }

}