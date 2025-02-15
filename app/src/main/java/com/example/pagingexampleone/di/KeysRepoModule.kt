package com.example.pagingexampleone.di

import com.example.pagingexampleone.data.repositories.KeysRepo
import com.example.pagingexampleone.data.repositories.KeysRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class KeysRepoModule {

    @ViewModelScoped
    @Binds
    abstract fun bindKeysRepoModule(keysRepo: KeysRepoImpl) : KeysRepo
}