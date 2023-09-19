package com.mehedisoftdev.productsapps.di

import android.content.Context
import androidx.room.Room
import com.mehedisoftdev.productsapps.db.ProductDatabase
import com.mehedisoftdev.productsapps.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun providesProductDatabase(@ApplicationContext context: Context): ProductDatabase {
        return Room.databaseBuilder(context,
            ProductDatabase::class.java,
            Constants.DATABASE_NAME)
            .build()
    }
}