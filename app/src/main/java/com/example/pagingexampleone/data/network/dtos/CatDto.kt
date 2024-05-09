package com.example.pagingexampleone.data.network.dtos

import com.example.pagingexampleone.core.models.DataModel
import com.google.gson.annotations.SerializedName

data class CatDto(
    @SerializedName("id")
    override val id: String,
    @SerializedName("url")
    val url: String
) : DataModel()