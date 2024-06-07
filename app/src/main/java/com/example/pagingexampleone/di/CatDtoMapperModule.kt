package com.example.pagingexampleone.di

import com.example.pagingexampleone.core.mappers.ModelMapper
import com.example.pagingexampleone.domain.models.CatModel
import com.example.pagingexampleone.data.network.dtos.cat.CatDto
import com.example.pagingexampleone.data.network.dtos.cat.CatDtoMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class CatDtoMapperModule {
    @ViewModelScoped
    @Binds
    abstract fun providesCatDtoMapper(catDtoMapper: CatDtoMapper) : ModelMapper<CatDto, CatModel>
}