package com.app.boltfax.base

data class UserDataModel(
    val fullName: String? = "",
    val password: String? = "",
    val contact: String? = "",
    val countryCode: String? = "",
    var documentName: String? = "",
)

data class BannerAndLogoModel(
    val imageUrl: String? = ""
)

data class ItemModel(
    val productName: String? = "",
    val imageUrl: String? = "",
    val sellingPrice: String? = "",
    val totalPrice: String? = "",
    val discount: String? = ""
)

data class CategoryModel(
    val categoryName: String? = "",
    val categoryImageUrl: String? = "",
    val categoryDescription: String? = ""
)