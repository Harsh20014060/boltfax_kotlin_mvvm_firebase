package com.app.boltfax.util

import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object PasswordUtil {

    fun generateSaltedKey(name: String, documentName: String): String {
//        val minLength = minOf(name.length, mobile.length)
//        val zippedPart =
//            name.zip(mobile).take(minLength).joinToString("") { "${it.first}${it.second}" }
////        val remainingPart = if (name.length > mobile.length) name.substring(minLength) else mobile.substring(minLength)
//        return zippedPart

        var key = ""

        for (i in name.indices) {
            try {
                key = key + name[i] + documentName[i]
            } catch (_: Exception) {
            }
        }
        return key
    }

    fun encrypt(keyValue: String, password: String): String {

        val key = generateKey(keyValue)
        val cipher = Cipher.getInstance("AES").apply {
            init(Cipher.ENCRYPT_MODE, key)
        }
        val encVal = cipher.doFinal(password.toByteArray())
        return Base64.encodeToString(encVal, Base64.DEFAULT)
    }

    fun decrypt(keyValue: String, encryptedPass: String): String {
        val key = generateKey(keyValue)

        val c = Cipher.getInstance("AES")

        c.init(Cipher.DECRYPT_MODE, key)

        val decodedvalue: ByteArray = Base64.decode(encryptedPass, Base64.DEFAULT)
        val decval = c.doFinal(decodedvalue)

        val decryptedValue = String(decval)
        return decryptedValue
    }


    private fun generateKey(keyData: String): SecretKeySpec {


        val key = MessageDigest.getInstance("SHA-256")
            .digest(keyData.toByteArray(Charsets.UTF_8))
        return SecretKeySpec(key, "AES")
    }
}