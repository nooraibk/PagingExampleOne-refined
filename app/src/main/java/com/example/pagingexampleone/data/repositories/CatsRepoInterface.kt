package com.example.pagingexampleone.data.repositories

import androidx.paging.PagingData
import com.example.pagingexampleone.data.network.dtos.CatDto
import kotlinx.coroutines.flow.Flow

interface CatsRepoInterface {
    fun getCats() : Flow<PagingData<CatDto>>

}
