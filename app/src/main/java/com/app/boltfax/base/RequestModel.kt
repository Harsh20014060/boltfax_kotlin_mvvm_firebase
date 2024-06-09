package com.app.boltfax.base

data class UserDataRequestModel(
    val fullName: String? = "",
    val password: String? = "",
    val confirmPassword: String? = "",
    val contact: String? = "",
    val countryCode: String? = "",
    val termsAndCondition: Boolean? = false,

    )