package com.example.pagingexampleone.data.network.dtos.cat

import com.example.pagingexampleone.domain.models.DataModel
import com.google.gson.annotations.SerializedName

data class CatDto(
    @SerializedName("id")
    override val id: String,
    @SerializedName("url")
    val url: String
) : DataModel()