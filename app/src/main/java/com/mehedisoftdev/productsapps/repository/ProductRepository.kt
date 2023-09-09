package com.mehedisoftdev.productsapps.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mehedisoftdev.productsapps.api.ProductsApi
import com.mehedisoftdev.productsapps.db.ProductDatabase
import com.mehedisoftdev.productsapps.models.Product
import com.mehedisoftdev.productsapps.utils.Network
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val context: Context,
    private val productsApi: ProductsApi,
    private val productDatabase: ProductDatabase
) {
    private var _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    suspend fun getProducts() {
        if(Network.isOnline(context)) {
            val results = productsApi.getProducts()
            if(results.isSuccessful && results.body() != null) {
                // store fetched data to local cache
                productDatabase.getProductDao().saveProducts(results.body()!!)
                // update products
                _products.postValue(results.body()!!)
            }
        }else {
            _products.postValue(productDatabase.getProductDao().getOfflineProducts())
        }
    }
}

