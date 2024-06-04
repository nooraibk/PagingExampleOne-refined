package com.example.pagingexampleone.core.mappers

interface ModelMapper<T, Domain> {
    fun mapToModel(obj : T) : Domain
    fun mapFromDomain(domain: Domain): T
}