package com.example.pagingexampleone.ui.activities.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pagingexampleone.R
import com.example.pagingexampleone.core.Constants.CATS_DATA_TYPE
import com.example.pagingexampleone.core.utils.DataType
import com.example.pagingexampleone.databinding.ActivityMainBinding
import com.example.pagingexampleone.ui.fragments.catslist.CatsListFragment
import com.example.pagingexampleone.ui.fragments.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragments_container, HomeFragment())
            addToBackStack(null)
            commit()
        }
    }

    fun moveToListFragment(dataType : DataType) {
        val catsList = CatsListFragment()
        val bundle = Bundle()
        bundle.putSerializable(CATS_DATA_TYPE, dataType)
        catsList.arguments = bundle
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragments_container, catsList)
            addToBackStack(null)
            commit()
        }
    }
}