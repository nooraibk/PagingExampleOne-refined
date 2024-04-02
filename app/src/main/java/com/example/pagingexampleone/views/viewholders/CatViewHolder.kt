package com.example.pagingexampleone.views.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pagingexampleone.R
import com.example.pagingexampleone.data.network.dtos.CatDto
import com.example.pagingexampleone.databinding.ItemCatBinding

class CatViewHolder (private val catBinding : ItemCatBinding) :
    RecyclerView.ViewHolder(catBinding.root) {
    fun bind(item : CatDto?){
        catBinding.imageCat.load(item?.url){
            placeholder(R.drawable.ic_placeholder)
        }
    }

    companion object{
        fun create(view : ViewGroup) : CatViewHolder{
            val inflater = LayoutInflater.from(view.context)
            val binding = ItemCatBinding.inflate(inflater, view, false)
            return CatViewHolder(binding)
        }
    }
}