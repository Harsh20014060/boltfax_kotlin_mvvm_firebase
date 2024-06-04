package com.app.boltfax.authModule.repository


import android.app.Activity
import android.util.Log
import com.app.boltfax.base.Resource
import com.app.boltfax.base.UserDataModel
import com.app.boltfax.util.FireBaseConstant
import com.app.boltfax.util.FireBaseConstant.DATABASE_USER_KEY_COUNTRY_CODE
import com.app.boltfax.util.FireBaseConstant.DATABASE_USER_KEY_FULL_NAME
import com.app.boltfax.util.FireBaseConstant.DATABASE_USER_KEY_PASSWORD
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class AuthRepository {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val dataBase by lazy { FirebaseFirestore.getInstance() }

    suspend fun login(contactNumber: String): Resource<UserDataModel> {
        return checkUser(contactNumber)

    }

    suspend fun generateOTP(activity: Activity, contactNumber: String): Resource<String> {
        val deferred = CompletableDeferred<Resource<String>>()

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(contactNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity).setCallbacks(object : OnVerificationStateChangedCallbacks() {
                override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                    deferred.complete(Resource.Success(s))
                }

                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    //signInWithPhoneAuthCredential(phoneAuthCredential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    deferred.complete(Resource.Error(message = e.message ?: "Unknown error"))
                }
            }) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

        return withContext(Dispatchers.IO) {
            deferred.await()
        }
    }

    suspend fun verifyOTP(otpID: String, otp: String): Resource<String> {
        val credential = PhoneAuthProvider.getCredential(otpID, otp)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {

                Log.d("TAG", "verifyOTP: Success...$it")

            }
            .addOnFailureListener {
                Log.d("TAG", "verifyOTP: Failure...$it")
            }

        return Resource.Success("")

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
                        contact = contactNumber,
                        documentName = contact
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