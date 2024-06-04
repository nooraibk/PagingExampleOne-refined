package com.example.pagingexampleone.di

import android.content.Context
import androidx.room.Room
import com.example.pagingexampleone.core.Constants.CATS_DATABASE
import com.example.pagingexampleone.data.local.db.CatDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun provideDb( appContext: Context): CatDatabase {
        return Room.databaseBuilder(
            appContext, CatDatabase::class.java,
            CATS_DATABASE
        ).build()
    }
}