package com.mehedisoftdev.productsapps.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mehedisoftdev.productsapps.models.Product
import com.mehedisoftdev.productsapps.utils.Constants

@Database(entities = [Product::class], version = Constants.DB_VERSION)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
}