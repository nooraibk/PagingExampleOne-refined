package com.example.pagingexampleone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.pagingexampleone.core.models.CatModel
import com.example.pagingexampleone.core.utils.NetworkDataDiffUtils
import com.example.pagingexampleone.databinding.ItemCatBinding
import com.example.pagingexampleone.ui.viewholders.CatViewHolder

class CatsPagingDataAdapter : PagingDataAdapter<CatModel, CatViewHolder>(NetworkDataDiffUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCatBinding.inflate(inflater, parent, false)
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        getItem(position)?.let {
            holder bind it
        }
    }
}