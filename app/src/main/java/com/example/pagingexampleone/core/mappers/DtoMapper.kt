package com.example.pagingexampleone.core.mappers

interface DtoMapper<Dto, Domain> {

    fun Dto.mapFromDto() : Domain

    fun Domain.mapToDto(): Dto
}