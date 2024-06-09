package com.app.boltfax.util

import android.content.Context
import android.content.SharedPreferences
import com.app.boltfax.base.UserDataModel
import com.google.gson.Gson


object MySharedPreference {

    private lateinit var prefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor
    private val PREFS_NAME = "drtc"

    fun clear(context: Context) {

        prefsEditor.clear()
        prefsEditor.apply()
    }


    fun setUserData(userDats: UserDataModel) {


        val gson = Gson();
        val json = gson.toJson(userDats)
        prefsEditor.putString("userData", json)
        prefsEditor.commit()
    }

    fun getUserData(): UserDataModel? {
        return try {
            val gson = Gson()
            val json: String = prefs.getString("userData", "") ?: ""
            gson.fromJson(json, UserDataModel::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            UserDataModel()
        }

    }

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefsEditor = prefs.edit()

    }

}

