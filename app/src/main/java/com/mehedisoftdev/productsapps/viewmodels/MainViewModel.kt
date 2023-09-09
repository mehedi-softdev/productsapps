package com.mehedisoftdev.productsapps.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehedisoftdev.productsapps.models.Product
import com.mehedisoftdev.productsapps.repository.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor (private val productRepository: ProductRepository) : ViewModel() {
    val products: LiveData<List<Product>>
        get() = productRepository.products

    init {
        // when main viewmodel is create
        viewModelScope.launch {
            productRepository.getProducts()
        }
    }
}