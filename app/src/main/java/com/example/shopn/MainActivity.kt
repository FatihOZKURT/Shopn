package com.example.shopn

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.shopn.databinding.ActivityMainBinding
import com.example.shopn.databinding.MainScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        val navController = fragment?.findNavController()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_home -> {
                    navController?.navigate(R.id.mainScreen)
                    true
                }
                R.id.menu_cart -> {
                    navController?.navigate(R.id.cartScreen)
                    true
                }
                else -> false
            }
        }
    }


}