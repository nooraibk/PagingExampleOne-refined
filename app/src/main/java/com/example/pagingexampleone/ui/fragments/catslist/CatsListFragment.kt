package com.example.pagingexampleone.ui.fragments.catslist

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.pagingexampleone.core.Constants.CATS_DATA_TYPE
import com.example.pagingexampleone.core.bases.BaseFragment
import com.example.pagingexampleone.core.utils.DataType
import com.example.pagingexampleone.databinding.FragmentCatsListBinding
import com.example.pagingexampleone.ui.adapters.CatsLoadStateAdapter
import com.example.pagingexampleone.ui.adapters.CatsPagingDataAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatsListFragment : BaseFragment<FragmentCatsListBinding>(
    FragmentCatsListBinding::inflate
) {
    private val catsAdapter = CatsPagingDataAdapter()

    override fun viewInitialized() {
        binding.apply {
            initAdapter()
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.setCats(arguments?.getSerializable(CATS_DATA_TYPE) as DataType).collectLatest {
                    catsAdapter.submitData(it)
                }
            }
        }
    }

    private fun initAdapter(){
        binding.recyclerView.adapter = catsAdapter.withLoadStateHeaderAndFooter(
            header = CatsLoadStateAdapter{catsAdapter.retry()},
            footer = CatsLoadStateAdapter{catsAdapter.retry()}
        )
        catsAdapter.addLoadStateListener { combinedLoadStates ->
            binding.apply{
                recyclerView.isVisible = combinedLoadStates.refresh is LoadState.NotLoading
                progressBar.isVisible = combinedLoadStates.refresh is LoadState.Loading
                buttonRetry.isVisible = combinedLoadStates.refresh is LoadState.Error
                handleError(combinedLoadStates)
            }
        }
        binding.buttonRetry.setOnClickListener {
            catsAdapter.retry()
        }
    }

    private fun handleError(combinedLoadStates: CombinedLoadStates) {
        val errorState = combinedLoadStates.source.append as? LoadState.Error
            ?: combinedLoadStates.source.prepend as? LoadState.Error

        errorState.let {
            Toast.makeText(requireContext(), "${it?.error}", Toast.LENGTH_SHORT).show()
        }
    }

}