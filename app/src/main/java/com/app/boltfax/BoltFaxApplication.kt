package com.app.boltfax

import android.app.Application
import com.app.boltfax.util.MySharedPreference


class BoltFaxApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MySharedPreference.init(this)
    }
}