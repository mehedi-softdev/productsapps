package com.mehedisoftdev.productsapps

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.google.gson.Gson
import com.mehedisoftdev.productsapps.databinding.FragmentParticularProductViewBinding
import com.mehedisoftdev.productsapps.models.Product
import com.mehedisoftdev.productsapps.utils.AppManager
import com.mehedisoftdev.productsapps.utils.Constants.PRODUCT

class ParticularProductViewFragment : Fragment() {
    private var _binding: FragmentParticularProductViewBinding? = null
    private val binding get() = _binding!!
    private var rewardedAd: RewardedAd? = null
    private var nativeAd: NativeAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParticularProductViewBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productJson: String? = arguments?.getString(PRODUCT)
        if (productJson != null) {

            val product: Product = Gson().fromJson(productJson, Product::class.java)
            bindObjects(product)

        } else {
            Toast.makeText(context, "CAN'T LOAD DATA", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        // load ads
        loadRewardedAds()
        loadNativeAd()// native
        binding.btnBuy.setOnClickListener {
            showRewardedAd()
        }
    }

    private fun showRewardedAd() {
        rewardedAd?.let { ad ->
            ad.show(requireActivity(), OnUserEarnedRewardListener { rewardItem ->
                // Handle the reward.
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
                AppManager.showMessage("For $$rewardAmount you bought the item.", requireContext())
                loadRewardedAds() // reload
            })
        } ?: run {
            AppManager.showMessage("Try later.", requireContext())
            loadRewardedAds()
        }
    }


    private fun bindObjects(product: Product) {
        Glide.with(this).load(product.image)
            .into(binding.productImage)
        binding.productTitle.text = product.title
        binding.productDesc.text = product.description
        binding.productPrice.text = "$".plus(product.price.toString())
    }


    private fun loadRewardedAds() {
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(
            requireContext(),
            "ca-app-pub-3940256099942544/5224354917",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                }
            })
    }


    // for native ads
    private fun loadNativeAd() {
        val adLoader = AdLoader.Builder(requireContext(), "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { ad: NativeAd ->
                // Handle the loaded native ad
                nativeAd = ad
                val adView = layoutInflater.inflate(R.layout.native_ad_layout, null) as NativeAdView
                populateNativeAdView(ad, adView)
                binding.nativeAdContainer.removeAllViews()
                binding.nativeAdContainer.addView(adView)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    retryLoadNativeAdAfterDelay(30000)
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun retryLoadNativeAdAfterDelay(delayMillis: Long) {
        Handler(Looper.getMainLooper()).postDelayed({
            loadNativeAd()
        }, delayMillis)
    }

    private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        // Populate the native ad components here based on your layout
        adView.headlineView = adView.findViewById(R.id.native_ad_headline)
        adView.iconView = adView.findViewById(R.id.native_ad_icon)
        adView.advertiserView = adView.findViewById(R.id.native_ad_advertiser)
        adView.bodyView = adView.findViewById(R.id.native_ad_body)
        adView.mediaView = adView.findViewById(R.id.native_ad_media)
        adView.callToActionView = adView.findViewById(R.id.native_ad_call_to_action)

        // Set the native ad views
        (adView.headlineView as TextView).text = nativeAd.headline
        (adView.advertiserView as TextView).text = nativeAd.advertiser
        (adView.bodyView as TextView).text = nativeAd.body

        // The MediaView will display a video asset if one is present in the ad, otherwise it will
        // display a static image asset.
        (adView.mediaView as MediaView).mediaContent = nativeAd.mediaContent

        // Register the native ad view for user interaction
        adView.setNativeAd(nativeAd)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}