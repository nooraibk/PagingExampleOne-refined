package com.example.pagingexampleone.ui.fragments.home

import com.example.pagingexampleone.core.bases.BaseFragment
import com.example.pagingexampleone.core.utils.DataType
import com.example.pagingexampleone.databinding.FragmentHomeBinding
import com.example.pagingexampleone.ui.activities.home.MainActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    override fun viewInitialized() {
        binding.apply {
            localCats.setOnClickListener {
                (requireActivity() as? MainActivity)?.moveToListFragment(DataType.Local.dataCode)
            }
            remoteCats.setOnClickListener {
                (requireActivity() as? MainActivity)?.moveToListFragment(DataType.Network.dataCode)
            }
            localAndRemoteCats.setOnClickListener {
                (requireActivity() as? MainActivity)?.moveToListFragment(DataType.Mediator.dataCode)
            }
        }
    }

}