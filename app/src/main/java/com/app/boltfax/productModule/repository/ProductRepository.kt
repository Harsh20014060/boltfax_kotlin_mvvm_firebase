package com.app.boltfax.productModule.repository

import android.util.Log
import com.app.boltfax.base.ItemModel
import com.app.boltfax.base.Resource
import com.app.boltfax.util.FireBaseConstant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class ProductRepository() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val dataBase by lazy { FirebaseFirestore.getInstance() }

    suspend fun getSearchItems(): Resource<ArrayList<ItemModel>> = coroutineScope {
        val categoryList = arrayOf(
            FireBaseConstant.DATABASE_EARBUDS,
            FireBaseConstant.DATABASE_EARPHONE,
            FireBaseConstant.DATABASE_HEADPHONE,
            FireBaseConstant.DATABASE_LAPTOP,
            FireBaseConstant.DATABASE_MOBILE,
            FireBaseConstant.DATABASE_NECKBAND
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
                            productName = product[FireBaseConstant.DATABASE_PRODUCT_KEY_PRODUCT_NAME].toString(),
                            imageUrl = product[FireBaseConstant.DATABASE_PRODUCT_KEY_PRODUCT_IMAGE].toString(),
                            sellingPrice = product[FireBaseConstant.DATABASE_PRODUCT_KEY_PRODUCT_SELLING_PRICE].toString(),
                            discount = product[FireBaseConstant.DATABASE_PRODUCT_KEY_PRODUCT_DISCOUNT].toString()
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