package com.example.pagingexampleone.core.mappers

interface DtoMapper<Dto, Domain> {
    fun mapFromDto(dto : Dto) : Domain
    fun mapToDto(domain: Domain): Dto
}