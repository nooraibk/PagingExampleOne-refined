package com.example.pagingexampleone.views.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.pagingexampleone.views.viewholders.CatsLoadStateViewHolder

class CatsLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<CatsLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: CatsLoadStateViewHolder, loadState: LoadState) {
        holder.bindLoadStateView(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = CatsLoadStateViewHolder.create(parent, retry)
}