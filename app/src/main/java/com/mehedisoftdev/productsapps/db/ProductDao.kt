package com.mehedisoftdev.productsapps.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mehedisoftdev.productsapps.models.Product

@Dao
interface ProductDao {
    // conflict strategy : replace existence data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProducts(products: List<Product>)

    @Query("SELECT * FROM products")
    suspend fun getOfflineProducts(): List<Product>
}