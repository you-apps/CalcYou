package net.youapps.calcyou

import android.app.Application
import android.content.Context

class CalculatorApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Companion.applicationContext = this.applicationContext
    }

    companion object {
        lateinit var applicationContext: Context
    }
}