package com.longnguyen.beautytodo.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.longnguyen.beautytodo.BuildConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
        init()
    }

    private fun init() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }
}