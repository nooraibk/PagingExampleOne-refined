package com.example.pagingexampleone.views.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
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

    fun bindLoadStateView(loadState: LoadState){
        loadStateBinding.apply {
            if (loadState is LoadState.Error){
                errorMsg.text = loadState.error.localizedMessage
            }

            progressBar.isVisible = loadState is LoadState.Loading
            errorMsg.isVisible = loadState is LoadState.Error
            retryButton.isVisible = loadState is LoadState.Error
        }
    }

    companion object{
        fun create(viewGroup: ViewGroup, retry: () -> Unit) : CatsLoadStateViewHolder{
            val view = LayoutInflater.from(viewGroup.context)
            val inflater = ItemLoadStateBinding.inflate(view, viewGroup, false)
            val binding = ItemLoadStateBinding.bind(inflater.root)
            return CatsLoadStateViewHolder(binding, retry)
        }
    }
}
