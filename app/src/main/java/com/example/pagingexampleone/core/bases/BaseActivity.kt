package com.example.pagingexampleone.core.bases

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity <VB : ViewBinding>(
    private val bindingInflater : (inflater : LayoutInflater) -> VB
) : AppCompatActivity(), CoroutineScope {
    val binding : VB by lazy { bindingInflater(layoutInflater) }

    override val coroutineContext: CoroutineContext
        get() = Job() + exception
    private val exception = CoroutineExceptionHandler{ coroutineContext, throwable ->
        cancel()
        throwable.printStackTrace()
    }

    abstract fun initializeViews()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeViews()
    }
}