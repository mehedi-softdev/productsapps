package com.mehedisoftdev.productsapps

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
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
                AppManager.showMessage("for $rewardAmount you bought the item.", requireContext())
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}