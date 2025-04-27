package com.example.pagingexampleone.data.network

import com.example.pagingexampleone.data.network.dtos.cat.CatDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApi {
    @GET("v1/images/search")
    suspend fun getCatImages(
        @Query("limit") size: Int,
        @Query("order") order: String = "Asc",
        @Query("page") page: Int
    ): List<CatDto>
}