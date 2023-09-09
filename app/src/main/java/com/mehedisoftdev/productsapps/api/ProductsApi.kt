package com.mehedisoftdev.productsapps.api

import com.mehedisoftdev.productsapps.models.Product
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {
    @GET("products/")
   suspend fun getProducts(): Response<List<Product>>
}