package com.example.pagingexampleone.data.network

import com.example.pagingexampleone.data.network.dtos.CatDataDto
import com.example.pagingexampleone.data.network.dtos.CatDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {
    @GET("v1/images/search")
    suspend fun getCatImages(
        @Query("limit") size: Int,
        @Query("order") order: String = "Asc",
        @Query("page") page: Int
    ): List<CatDataDto>
}