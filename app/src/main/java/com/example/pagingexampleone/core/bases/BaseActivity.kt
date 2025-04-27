package com.example.pagingexampleone.core.bases

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseActivity <VB : ViewBinding>(
    private val bindingInflater : (inflater : LayoutInflater) -> VB
) : AppCompatActivity() {
    val binding : VB by lazy { bindingInflater(layoutInflater) }

    abstract fun initializeViews()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeViews()
    }
}