package com.example.pagingexampleone.di

import com.example.pagingexampleone.data.repositories.CatsRepo
import com.example.pagingexampleone.data.repositories.CatsRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {

    @ViewModelScoped
    @Binds
    abstract fun bindRepository(tracksRepo : CatsRepoImpl) : CatsRepo
}