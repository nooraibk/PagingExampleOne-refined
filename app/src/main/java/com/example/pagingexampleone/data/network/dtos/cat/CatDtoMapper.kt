package com.example.pagingexampleone.data.network.dtos.cat

import com.example.pagingexampleone.core.mappers.ModelMapper
import com.example.pagingexampleone.core.models.CatModel
import javax.inject.Inject

class CatDtoMapper @Inject constructor(): ModelMapper<CatDto, CatModel> {

    override fun mapToModel(obj: CatDto) =
        CatModel(
            id = obj.id,
            url = obj.url
        )

    override fun mapFromDomain(domain: CatModel) =
        CatDto(
            id = domain.id,
            url = domain.url
        )

}