package com.example.pagingexampleone.data.network.dtos.cat

import com.example.pagingexampleone.core.mappers.DtoMapper
import com.example.pagingexampleone.core.models.Cat
import javax.inject.Inject

class CatDtoMapper @Inject constructor(): DtoMapper<CatDto, Cat> {

    override fun mapFromDto(dto: CatDto) =
        Cat(
            id = dto.id,
            url = dto.url
        )

    override fun mapToDto(domain: Cat) =
        CatDto(
            id = domain.id,
            url = domain.url
        )

}