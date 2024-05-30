package com.example.pagingexampleone.di

import com.example.pagingexampleone.core.mappers.DtoMapper
import com.example.pagingexampleone.core.models.Cat
import com.example.pagingexampleone.data.network.dtos.cat.CatDto
import com.example.pagingexampleone.data.network.dtos.cat.CatDtoMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CatDtoMapperModule {
    @Singleton
    @Binds
    abstract fun providesCatDtoMapper(catDtoMapper: CatDtoMapper) : DtoMapper<CatDto, Cat>
}