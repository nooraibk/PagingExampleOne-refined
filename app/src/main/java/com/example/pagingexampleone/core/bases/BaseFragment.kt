package com.example.pagingexampleone.core.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.pagingexampleone.core.showToast
import com.example.pagingexampleone.ui.activities.home.MainViewModel
import com.example.pagingexampleone.ui.adapters.CatsLoadStateAdapter
import com.example.pagingexampleone.ui.adapters.CatsPagingDataAdapter
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

    fun initAdapter(
        recyclerView: RecyclerView,
        progressBar : ProgressBar,
        buttonRetry : Button,
        catsAdapter: CatsPagingDataAdapter
    ) {
        recyclerView.adapter = catsAdapter.withLoadStateHeaderAndFooter(
            header = CatsLoadStateAdapter { catsAdapter.retry() },
            footer = CatsLoadStateAdapter { catsAdapter.retry() }
        )
        catsAdapter.addLoadStateListener { combinedLoadStates ->
            binding.apply {
                recyclerView.isVisible = combinedLoadStates.refresh is LoadState.NotLoading
                progressBar.isVisible = combinedLoadStates.refresh is LoadState.Loading
                buttonRetry.isVisible = combinedLoadStates.refresh is LoadState.Error
                handleError(combinedLoadStates)
            }
        }
        buttonRetry.setOnClickListener {
            catsAdapter.retry()
        }
    }

    private fun handleError(combinedLoadStates: CombinedLoadStates) {
        val errorState = combinedLoadStates.source.append as? LoadState.Error
            ?: combinedLoadStates.source.prepend as? LoadState.Error

        errorState.let {
            requireContext().showToast(it?.error.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}