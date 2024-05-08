package com.example.pagingexampleone.data.network.dtos

import com.example.pagingexampleone.core.models.DataWrapper

data class CatDataDto(
    override val id: String,
    val url: String
) : DataWrapper()