package com.example.pagingexampleone.di

import com.example.pagingexampleone.core.mappers.ModelMapper
import com.example.pagingexampleone.data.local.entities.cat.CatEntity
import com.example.pagingexampleone.data.local.entities.cat.CatEntityMapper
import com.example.pagingexampleone.domain.models.CatModel
import com.example.pagingexampleone.data.network.dtos.cat.CatDto
import com.example.pagingexampleone.data.network.dtos.cat.CatDtoMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class MapperModule {

    @ViewModelScoped
    @Binds
    abstract fun providesCatDtoMapper(catDtoMapper: CatDtoMapper) : ModelMapper<CatDto, CatModel>

    @ViewModelScoped
    @Binds
    abstract fun providesCatEntityMapper(catEntityMapper : CatEntityMapper) : ModelMapper<CatEntity, CatModel>
}