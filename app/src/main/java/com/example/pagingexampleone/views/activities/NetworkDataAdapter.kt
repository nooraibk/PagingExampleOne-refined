package com.example.pagingexampleone.views.activities

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.pagingexampleone.core.utils.NetworkDataDiffUtils
import com.example.pagingexampleone.data.network.dtos.CatDto
import com.example.pagingexampleone.views.viewholders.CatViewHolder

class NetworkDataAdapter : PagingDataAdapter<CatDto, CatViewHolder>(NetworkDataDiffUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CatViewHolder.create(parent)
    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}