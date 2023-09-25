package com.mehedisoftdev.productsapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.mehedisoftdev.productsapps.adapters.ProductListAdapter
import com.mehedisoftdev.productsapps.databinding.ActivityMainBinding
import com.mehedisoftdev.productsapps.utils.InterstitialAdManager
import com.mehedisoftdev.productsapps.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        val adRequest = AdRequest.Builder().build()
        mAdView = AdView(this)
//        test id
        mAdView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        binding.adView.loadAd(adRequest)
        // interstitial ads
        InterstitialAdManager.loadInterstitialAd(this, adRequest)
    }
}