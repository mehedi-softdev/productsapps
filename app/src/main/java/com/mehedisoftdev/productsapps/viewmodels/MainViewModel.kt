package com.mehedisoftdev.productsapps.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehedisoftdev.productsapps.models.Product
import com.mehedisoftdev.productsapps.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val productRepository: ProductRepository) : ViewModel() {
    val products: LiveData<List<Product>>
        get() = productRepository.products

    init {
        // when main view model is create
        viewModelScope.launch {
            productRepository.getProducts()
        }
    }
}