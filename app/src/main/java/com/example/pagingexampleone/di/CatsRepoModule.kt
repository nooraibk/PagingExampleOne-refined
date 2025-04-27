package com.example.pagingexampleone.di

import com.example.pagingexampleone.data.repositories.CatsRepo
import com.example.pagingexampleone.data.repositories.CatsRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class CatsRepoModule {

    @ViewModelScoped
    @Binds
    abstract fun bindCatsRepository(catsRepo : CatsRepoImpl) : CatsRepo
}