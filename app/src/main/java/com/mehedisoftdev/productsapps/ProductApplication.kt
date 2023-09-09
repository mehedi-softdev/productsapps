package com.mehedisoftdev.productsapps

import android.app.Application
import com.mehedisoftdev.productsapps.di.ApplicationComponent
import com.mehedisoftdev.productsapps.di.DaggerApplicationComponent


class ProductApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}