package com.app.boltfax.authModule.viewModel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.boltfax.authModule.repository.AuthRepository
import com.app.boltfax.base.Resource
import com.app.boltfax.base.UserDataModel
import com.app.boltfax.base.UserDataRequestModel
import com.app.boltfax.util.PasswordUtil
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val authRepository by lazy { AuthRepository() }

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

    val otpObserver: MutableLiveData<Resource<Pair<String, PhoneAuthProvider.ForceResendingToken>>> by lazy {
        MutableLiveData<Resource<Pair<String, PhoneAuthProvider.ForceResendingToken>>>()
    }

    fun generateOTP(
        activity: Activity,
        contact: String,
        resendToken: PhoneAuthProvider.ForceResendingToken? = null

        ) {
        synchronized(otpObserver) {
            showLoader.postValue(true)
        }
        viewModelScope.launch {
            authRepository.generateOTP(activity, contact, resendToken).let {

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
            if (otp.isBlank()) {
                verifyOTPObserver.postValue(Resource.Error("Otp required"))
            }
            authRepository.verifyOTP(otpID, otp).let {

                showLoader.postValue(false)
                verifyOTPObserver.postValue(it)
            }
        }

    }

    val signupObserver: MutableLiveData<Resource<UserDataModel>> by lazy {
        MutableLiveData<Resource<UserDataModel>>()
    }

    fun signup(signUpData: UserDataRequestModel) {
        synchronized(signupObserver) {
            showLoader.postValue(true)
        }
        viewModelScope.launch {
            if (signUpData.fullName!!.isBlank()) {
                signupObserver.postValue(Resource.Error("Full name required"))
            } else if (signUpData.countryCode!!.isBlank()) {
                signupObserver.postValue(Resource.Error("Country code required"))
            } else if (signUpData.contact!!.isBlank()) {
                signupObserver.postValue(Resource.Error("Contact required"))
            } else if (signUpData.password != signUpData.confirmPassword) {
                signupObserver.postValue(Resource.Error("Password doesn't match"))
            } else if (!signUpData.termsAndCondition!!) {
                signupObserver.postValue(Resource.Error("Accept terms and condition"))
            } else {
                val documentName =
                    "${signUpData.contact.substring(0, 5)} ${signUpData.contact.substring(5)}"
                authRepository.signup(
                    UserDataModel(
                        fullName = signUpData.fullName,
                        password = PasswordUtil.encrypt(
                            PasswordUtil.generateSaltedKey(
                                signUpData.fullName, documentName
                            ), signUpData.password ?: ""
                        ),
                        contact = signUpData.contact,
                        countryCode = signUpData.countryCode,
                        documentName = documentName
                    )
                ).let {
                    showLoader.postValue(false)
                    signupObserver.postValue(it)
                }
            }
        }


    }

}