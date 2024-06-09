package com.app.boltfax.mainModule.repository

import android.util.Log
import com.app.boltfax.base.BannerAndLogoModel
import com.app.boltfax.base.CategoryModel
import com.app.boltfax.base.ItemModel
import com.app.boltfax.base.Resource
import com.app.boltfax.util.FireBaseConstant.DATABASE_BRAND_LOGOS
import com.app.boltfax.util.FireBaseConstant.DATABASE_BRAND_LOGOS_IMAGE_KEY
import com.app.boltfax.util.FireBaseConstant.DATABASE_CATEGORY
import com.app.boltfax.util.FireBaseConstant.DATABASE_CATEGORY_KEY_DESCRIPTION
import com.app.boltfax.util.FireBaseConstant.DATABASE_CATEGORY_KEY_IMAGE
import com.app.boltfax.util.FireBaseConstant.DATABASE_CATEGORY_KEY_NAME
import com.app.boltfax.util.FireBaseConstant.DATABASE_EARBUDS
import com.app.boltfax.util.FireBaseConstant.DATABASE_EARPHONE
import com.app.boltfax.util.FireBaseConstant.DATABASE_HEADPHONE
import com.app.boltfax.util.FireBaseConstant.DATABASE_HOME_BANNER
import com.app.boltfax.util.FireBaseConstant.DATABASE_HOME_DOCUMENT
import com.app.boltfax.util.FireBaseConstant.DATABASE_LAPTOP
import com.app.boltfax.util.FireBaseConstant.DATABASE_MOBILE
import com.app.boltfax.util.FireBaseConstant.DATABASE_NECKBAND
import com.app.boltfax.util.FireBaseConstant.DATABASE_PRODUCT_KEY_PRODUCT_DISCOUNT
import com.app.boltfax.util.FireBaseConstant.DATABASE_PRODUCT_KEY_PRODUCT_IMAGE
import com.app.boltfax.util.FireBaseConstant.DATABASE_PRODUCT_KEY_PRODUCT_NAME
import com.app.boltfax.util.FireBaseConstant.DATABASE_PRODUCT_KEY_PRODUCT_SELLING_PRICE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainRepository {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val dataBase by lazy { FirebaseFirestore.getInstance() }

    suspend fun getHomeBanners(): Resource<ArrayList<BannerAndLogoModel>> {

        return withContext(Dispatchers.IO) {
            try {
                val documentData =
                    dataBase.collection(DATABASE_HOME_BANNER).document(DATABASE_HOME_DOCUMENT).get()
                        .await()

                val imageList = ArrayList<BannerAndLogoModel>()
                if (documentData.exists()) {
                    val data = documentData.data
                    data?.let { it ->
                        for ((key, value) in it) {
                            val url = value as? String
                            url?.let {

                                imageList.add(BannerAndLogoModel(url))

                            }
                        }
                    }

                    Resource.Success(imageList)
                } else {
                    Resource.DataNotFound(message = "Banner not found")
                }
            } catch (e: Exception) {
                Resource.Error(e.message)

            }

        }
    }

    suspend fun getBrandLogos(): Resource<ArrayList<BannerAndLogoModel>> = coroutineScope {

        val itemList = ArrayList<BannerAndLogoModel>()
        val tasks =
            async {
                val querySnapshot: QuerySnapshot =
                    dataBase.collection(DATABASE_BRAND_LOGOS).get().await()
                querySnapshot.documents.forEach { brand ->
                    Log.d("TAG", "getTrendingAndDayDealsData: $brand")
                    itemList.add(
                        BannerAndLogoModel(

                            imageUrl = brand[DATABASE_BRAND_LOGOS_IMAGE_KEY].toString(),

                            )
                    )
                }

            }

        // Await completion of all async tasks
        tasks.await()

        // Return the result as a Resource
        Resource.Success(itemList)
    }

    suspend fun getCategories(): Resource<ArrayList<CategoryModel>> = coroutineScope {
        val categoryList = ArrayList<CategoryModel>()
        val tasks =
            async {
                val querySnapshot: QuerySnapshot =
                    dataBase.collection(DATABASE_CATEGORY).get().await()
                querySnapshot.documents.forEach { category ->
                    Log.d("TAG", "getTrendingAndDayDealsData: $category")
                    categoryList.add(
                        CategoryModel(
                            categoryName = category[DATABASE_CATEGORY_KEY_NAME].toString(),
                            categoryImageUrl = category[DATABASE_CATEGORY_KEY_IMAGE].toString(),
                            categoryDescription = category[DATABASE_CATEGORY_KEY_DESCRIPTION].toString(),

                            )
                    )
                }

            }

        // Await completion of all async tasks
        tasks.await()

        // Return the result as a Resource
        Resource.Success(categoryList)
    }

    suspend fun getTrendingAndDayDealsData(): Resource<ArrayList<ItemModel>> = coroutineScope {
        val categoryList = arrayOf(
            DATABASE_EARBUDS,
            DATABASE_EARPHONE,
            DATABASE_HEADPHONE,
            DATABASE_LAPTOP,
            DATABASE_MOBILE,
            DATABASE_NECKBAND
        )

        val itemList = ArrayList<ItemModel>()


        // Use async to launch all Firestore queries concurrently
        val tasks = categoryList.map { series ->
            async {
                val querySnapshot: QuerySnapshot = dataBase.collection(series).get().await()
                querySnapshot.documents.forEach { product ->
                    Log.d("TAG", "getTrendingAndDayDealsData: $product")
                    itemList.add(
                        ItemModel(
                            productName = product[DATABASE_PRODUCT_KEY_PRODUCT_NAME].toString(),
                            imageUrl = product[DATABASE_PRODUCT_KEY_PRODUCT_IMAGE].toString(),
                            sellingPrice = product[DATABASE_PRODUCT_KEY_PRODUCT_SELLING_PRICE].toString(),
                            discount = product[DATABASE_PRODUCT_KEY_PRODUCT_DISCOUNT].toString()
                        )
                    )
                }
            }
        }

        // Await completion of all async tasks
        tasks.awaitAll()


        // Return the result as a Resource
        Resource.Success(itemList)
    }


}