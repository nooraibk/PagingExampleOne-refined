package com.example.pagingexampleone.ui.viewholders

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingexampleone.databinding.ItemLoadStateBinding

class CatsLoadStateViewHolder(
    private val loadStateBinding : ItemLoadStateBinding,
    retry : () -> Unit
) : RecyclerView.ViewHolder(loadStateBinding.root){
    init {
        loadStateBinding.retryButton.setOnClickListener { retry.invoke() }
    }

    infix fun bindLoadStateView(loadState: LoadState){
        loadStateBinding.apply {
            if (loadState is LoadState.Error){
                errorMsg.text = loadState.error.localizedMessage
            }

            progressBar.isVisible = loadState is LoadState.Loading
            errorMsg.isVisible = loadState is LoadState.Error
            retryButton.isVisible = loadState is LoadState.Error
        }
    }
}
