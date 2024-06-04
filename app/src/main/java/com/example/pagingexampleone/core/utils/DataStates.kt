package com.example.pagingexampleone.core.utils

import androidx.paging.PagingData
import com.example.pagingexampleone.core.models.Cat
import kotlinx.coroutines.flow.SharedFlow

sealed class DataStates {
    data object Loading : DataStates()
    data class Data(val data : SharedFlow<PagingData<Cat>>) : DataStates()
    data class Error(val message : String) : DataStates()
}