package com.example.pagingexampleone.data.network

import android.util.Log
import com.example.pagingexampleone.data.network.dtos.CatDto
import kotlinx.coroutines.delay
import java.io.IOException

class CatPaging(private val catApi: CatApi) : PagingSourceWrapper<CatDto>() {
    override suspend fun dataList(pageSize: Int, page: Int): List<CatDto> {
        return try {
            val response = catApi.getCatImages(
                size = pageSize,
                page = page
            )
            delay(1000L)
            response
        }catch (e : IOException){
            Log.d("HTTPException", e.localizedMessage?:" IOException")
            emptyList()
        }
    }
}
