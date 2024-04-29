package com.example.pagingexampleone.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pagingexampleone.R
import com.example.pagingexampleone.core.utils.DataType
import com.example.pagingexampleone.databinding.FragmentHomeBinding
import com.example.pagingexampleone.ui.activities.home.MainActivity
import com.example.pagingexampleone.ui.activities.home.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.apply {
            localCats.setOnClickListener {
//                viewModel.fillWithDummyCats()
                (requireActivity() as MainActivity).moveToListFragment(DataType.Local)
            }
            remoteCats.setOnClickListener {
                (requireActivity() as MainActivity).moveToListFragment(DataType.Network)
            }
            localAndRemoteCats.setOnClickListener {
                (requireActivity() as MainActivity).moveToListFragment(DataType.Mediator)
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}