package com.mehedisoftdev.productsapps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mehedisoftdev.productsapps.databinding.FragmentParticularProductViewBinding
import com.mehedisoftdev.productsapps.models.Product
import com.mehedisoftdev.productsapps.utils.Constants.PRODUCT

class ParticularProductViewFragment : Fragment() {
    private var _binding: FragmentParticularProductViewBinding? = null
    private val binding get() = _binding!!

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

    }

    private fun bindObjects(product: Product) {
        Glide.with(this).load(product.image)
            .into(binding.productImage)
        binding.productTitle.text = product.title
        binding.productDesc.text = product.description
        binding.productPrice.text = "$".plus( product.price.toString() )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}