package com.example.pagingexampleone.core.mappers

interface EntityMapper<Entity, Domain> {

    fun mapFromEntity(entity: Entity): Domain

    fun mapToEntity(domain: Domain): Entity
}