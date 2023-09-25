package com.mehedisoftdev.productsapps

import android.app.Application
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ProductApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // load ads
        MobileAds.initialize(this) {}
    }
}