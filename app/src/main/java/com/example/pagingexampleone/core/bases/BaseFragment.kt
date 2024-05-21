package com.example.pagingexampleone.core.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.example.pagingexampleone.ui.activities.home.MainViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment(), CoroutineScope {

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: VB? = null
    val binding: VB
        get() = _binding!!

    abstract fun viewInitialized()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override val coroutineContext: CoroutineContext
        get() = Job() + exception
    private val exception = CoroutineExceptionHandler { coroutineContext, throwable ->
        cancel()
        throwable.printStackTrace()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewInitialized()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}