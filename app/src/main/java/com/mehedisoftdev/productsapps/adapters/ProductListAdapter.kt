package com.mehedisoftdev.productsapps.adapters

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mehedisoftdev.productsapps.R
import com.mehedisoftdev.productsapps.models.Product
import com.mehedisoftdev.productsapps.utils.Constants.PRODUCT
import com.mehedisoftdev.productsapps.utils.InterstitialAdManager

class ProductListAdapter(private val activity: Activity) :
    ListAdapter<Product, ProductListAdapter.ProductViewHolder>(DiffUtilCallbacks()) {

    class ProductViewHolder(val context: Context, view: View) : ViewHolder(view) {
        val productImage = view.findViewById<ImageView>(R.id.product_image)
        val productTitle = view.findViewById<TextView>(R.id.product_title)
        val productPrice = view.findViewById<TextView>(R.id.product_price)

        fun bind(product: Product) {
            // setup image
            Glide.with(context).load(product.image).into(productImage)
            // title and price
            productTitle.text = product.title
            productPrice.text = "$ ".plus(product.price.toString())
        }
    }

    // diff util class
    class DiffUtilCallbacks : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_product_row, parent, false
        )

        return ProductViewHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
        holder.itemView.setOnClickListener {
            InterstitialAdManager.showInterstitialAd(activity)
            // move to specific product view activity
            val bundle = Bundle()
            bundle.putString(PRODUCT, Gson().toJson(product))
            holder.itemView.findNavController()
                .navigate(R.id.action_productsFragment_to_particularProductViewFragment, bundle)
        }
    }
}