package com.example.pagingexampleone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.pagingexampleone.databinding.ItemLoadStateBinding
import com.example.pagingexampleone.ui.viewholders.CatsLoadStateViewHolder

class CatsLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<CatsLoadStateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        CatsLoadStateViewHolder(
            ItemLoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), retry
        )

    override fun onBindViewHolder(holder: CatsLoadStateViewHolder, loadState: LoadState) {
        holder bindLoadStateView loadState
    }
}