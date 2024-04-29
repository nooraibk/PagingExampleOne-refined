package com.example.pagingexampleone.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.pagingexampleone.core.models.Cat

class NetworkDataDiffUtils : DiffUtil.ItemCallback<Cat>() {
    override fun areItemsTheSame(oldItem: Cat, newItem: Cat) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Cat, newItem: Cat) = oldItem == newItem
}