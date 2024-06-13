package com.example.pagingexampleone.di

import com.example.pagingexampleone.data.local.entities.cat.CatEntity
import com.example.pagingexampleone.data.network.pagingmediator.BaseRemoteMediator
import com.example.pagingexampleone.data.network.pagingmediator.CatsRemoteMediator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class PagingSourcesModule {

    @ViewModelScoped
    @Binds
    abstract fun bindCatRemoteMediator(catPagingSource : CatsRemoteMediator) : BaseRemoteMediator<CatEntity>
}