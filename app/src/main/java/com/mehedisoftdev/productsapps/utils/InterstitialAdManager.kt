package com.mehedisoftdev.productsapps.utils

import android.app.Activity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object InterstitialAdManager {
    private var mInterstitialAd: InterstitialAd? = null

    fun loadInterstitialAd(activity: Activity, adRequest: AdRequest) {
        InterstitialAd.load(
            activity,
            "ca-app-pub-3940256099942544/8691691433", // test id
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    // ad event
                    adEvent(activity)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    mInterstitialAd = null
                }
            }
        )


    }

    private fun adEvent(activity: Activity) {
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
               loadInterstitialAd(activity, AdRequest.Builder().build())
            }
        }
    }

    fun showInterstitialAd(activity: Activity) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity)
        } else {
            // If the ad is not loaded, load it again
            loadInterstitialAd(activity, AdRequest.Builder().build())
        }
    }
}
