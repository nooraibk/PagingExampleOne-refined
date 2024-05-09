package com.example.pagingexampleone.di

import com.example.pagingexampleone.data.repositories.CatsRepo
import com.example.pagingexampleone.data.repositories.CatsRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Singleton
    @Binds
    abstract fun bindRepository(tracksRepo : CatsRepoImpl) : CatsRepo
}