package com.example.pagingexampleone.di

import com.example.pagingexampleone.core.mappers.ModelMapper
import com.example.pagingexampleone.core.models.CatModel
import com.example.pagingexampleone.data.local.entities.cat.CatEntity
import com.example.pagingexampleone.data.local.entities.cat.CatEntityMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CatEntityMapperModule {

    @Singleton
    @Binds
    abstract fun providesCatEntityMapper(catEntityMapper : CatEntityMapper) : ModelMapper<CatEntity, CatModel>
}