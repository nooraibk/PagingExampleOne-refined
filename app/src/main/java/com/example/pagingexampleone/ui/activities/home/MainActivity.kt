package com.example.pagingexampleone.ui.activities.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pagingexampleone.R
import com.example.pagingexampleone.databinding.ActivityMainBinding
import com.example.pagingexampleone.ui.fragments.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
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
}