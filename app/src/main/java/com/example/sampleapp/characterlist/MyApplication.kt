package com.example.sampleapp.characterlist

import android.app.Application
import com.example.sampleapp.characterlist.di.ViewModelInjector

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}
