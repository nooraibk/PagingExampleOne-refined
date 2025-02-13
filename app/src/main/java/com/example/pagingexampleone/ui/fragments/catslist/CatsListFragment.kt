package com.example.pagingexampleone.ui.fragments.catslist

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.pagingexampleone.R
import com.example.pagingexampleone.core.CATS_DATA_TYPE
import com.example.pagingexampleone.core.bases.BaseFragment
import com.example.pagingexampleone.core.logIt
import com.example.pagingexampleone.core.showToastOf
import com.example.pagingexampleone.core.utils.DataType
import com.example.pagingexampleone.databinding.FragmentCatsListBinding
import com.example.pagingexampleone.domain.models.CatModel
import com.example.pagingexampleone.ui.adapters.CatsLoadStateAdapter
import com.example.pagingexampleone.ui.adapters.CatsPagingDataAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatsListFragment : BaseFragment<FragmentCatsListBinding>(
    FragmentCatsListBinding::inflate
) {

    private val catsAdapter by lazy { CatsPagingDataAdapter() }

    override fun viewInitialized() {
        initAdapter()
        val catType = arguments?.getInt(CATS_DATA_TYPE)?: -1
        val catEnum = DataType.toDataTypeEnum(catType)
        lifecycleScope.launch(Dispatchers.IO) {
            (this@CatsListFragment setCats catEnum).collectLatest {
                catsAdapter.submitData(it)
            }
        }
    }

    private infix fun setCats(catsType: DataType): StateFlow<PagingData<CatModel>> {
        return when (catsType) {
            DataType.Local -> {
                viewModel.localCats
            }

            DataType.Network -> {
                viewModel.remoteCats
            }

            DataType.Mediator -> {
                viewModel.mediatorCats
            }

            else -> {
                MutableStateFlow(PagingData.empty())
            }
        }
    }

    private fun initAdapter() {
        binding.apply {
            recyclerView.adapter = catsAdapter.withLoadStateHeaderAndFooter(
                header = CatsLoadStateAdapter { catsAdapter.retry() },
                footer = CatsLoadStateAdapter { catsAdapter.retry() }
            )
            catsAdapter.addLoadStateListener { combinedLoadStates ->
                recyclerView.isVisible = combinedLoadStates.refresh is LoadState.NotLoading
                progressBar.isVisible = combinedLoadStates.refresh is LoadState.Loading
                buttonRetry.isVisible = combinedLoadStates.refresh is LoadState.Error
                this@CatsListFragment handleErrorFor combinedLoadStates
            }
            buttonRetry.setOnClickListener {
                catsAdapter.retry()
            }
        }
    }

    private infix fun handleErrorFor(combinedLoadStates: CombinedLoadStates) {
        val errorState = combinedLoadStates.source.append as? LoadState.Error
            ?: combinedLoadStates.source.prepend as? LoadState.Error

        errorState.let {
            it?.error.toString().logIt("LoadStateError:: ")
            if (it?.error != null) {
                context showToastOf R.string.error_loading_data
            }
        }
    }
}