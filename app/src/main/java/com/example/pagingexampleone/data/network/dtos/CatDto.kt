package com.example.pagingexampleone.data.network.dtos

import com.example.pagingexampleone.core.mappers.DtoMapper
import com.example.pagingexampleone.core.models.Cat
import com.example.pagingexampleone.core.models.DataModel
import com.google.gson.annotations.SerializedName

data class CatDto(
    @SerializedName("id")
    override val id: String,
    @SerializedName("url")
    val url: String
) : DataModel(), DtoMapper<CatDto, Cat> {
    override fun CatDto.mapFromDto(): Cat {
        TODO("Not yet implemented")
    }

    override fun Cat.mapToDto(): CatDto {
        TODO("Not yet implemented")
    }

}