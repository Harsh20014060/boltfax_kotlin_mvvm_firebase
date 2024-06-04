package com.app.boltfax.authModule.repository


import com.app.boltfax.base.Resource
import com.app.boltfax.base.UserDataModel
import com.app.boltfax.util.FireBaseConstant
import com.app.boltfax.util.FireBaseConstant.DATABASE_USER_KEY_COUNTRY_CODE
import com.app.boltfax.util.FireBaseConstant.DATABASE_USER_KEY_FULL_NAME
import com.app.boltfax.util.FireBaseConstant.DATABASE_USER_KEY_PASSWORD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val dataBase = FirebaseFirestore.getInstance()

    suspend fun login(contactNumber: String): Resource<UserDataModel> {
        return checkUser(contactNumber)

    }

    private suspend fun checkUser(contactNumber: String): Resource<UserDataModel> {
        val contact = "${contactNumber.substring(0, 5)} ${contactNumber.substring(5)}"

        return withContext(Dispatchers.IO) {
            try {
                val documentSnapshot =
                    dataBase.collection(FireBaseConstant.DATABASE_USER).document(contact).get()
                        .await()

                if (documentSnapshot.exists()) {
                    val data = UserDataModel(
                        countryCode = documentSnapshot.data?.get(DATABASE_USER_KEY_COUNTRY_CODE)
                            .toString(),
                        fullName = documentSnapshot.data?.get(DATABASE_USER_KEY_FULL_NAME)
                            .toString(),
                        password = documentSnapshot.data?.get(DATABASE_USER_KEY_PASSWORD)
                            .toString(),
                        contact = contactNumber
                    )
                    Resource.Success(data = data)

                } else {
                    Resource.DataNotFound(message = "User not exists")
                }

            } catch (e: FirebaseFirestoreException) {
                // Handle Firestore exception
                Resource.Error(e.message)
            }
        }

    }


}