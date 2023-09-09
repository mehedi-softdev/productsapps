package com.mehedisoftdev.productsapps.di

import android.content.Context
import androidx.room.Room
import com.mehedisoftdev.productsapps.db.ProductDatabase
import com.mehedisoftdev.productsapps.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun providesProductDatabase(context: Context): ProductDatabase {
        return Room.databaseBuilder(context,
            ProductDatabase::class.java,
            Constants.DATABASE_NAME)
            .build()
    }
}