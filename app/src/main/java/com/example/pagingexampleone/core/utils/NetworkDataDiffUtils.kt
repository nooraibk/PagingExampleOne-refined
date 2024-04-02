package com.example.pagingexampleone.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.pagingexampleone.data.network.dtos.CatDto

class NetworkDataDiffUtils : DiffUtil.ItemCallback<CatDto>() {
    override fun areItemsTheSame(oldItem: CatDto, newItem: CatDto) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: CatDto, newItem: CatDto) = oldItem == newItem
}