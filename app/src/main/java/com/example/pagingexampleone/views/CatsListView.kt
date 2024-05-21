package com.example.pagingexampleone.views

import androidx.paging.PagingData
import com.example.pagingexampleone.core.models.Cat
import com.example.pagingexampleone.core.utils.DataType
import kotlinx.coroutines.flow.Flow

interface CatsListView {
    fun showLoader()
    fun hideLoader()
    fun setItems(dataType: DataType) : Flow<PagingData<Cat>>
    fun showError(message : String)
    fun hideError()
    fun showRetryButton()
    fun hideRetryButton()
}
