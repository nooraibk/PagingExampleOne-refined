package com.example.pagingexampleone.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pagingexampleone.R
import com.example.pagingexampleone.domain.models.CatModel
import com.example.pagingexampleone.databinding.ItemCatBinding

class CatViewHolder (private val catBinding : ItemCatBinding) :
    RecyclerView.ViewHolder(catBinding.root) {
    infix fun bind(item : CatModel?){
        catBinding.imageCat.load(item?.url){
            placeholder(R.drawable.ic_placeholder)
        }
    }
}