package com.example.shopn

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.shopn.databinding.ActivityMainBinding
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
                R.id.menu_favorite -> {
                    navController?.navigate(R.id.favoriteScreen)
                    true
                }
                R.id.menu_profile -> {
                    navController?.navigate(R.id.profileScreen)
                    true
                }
                else -> false
            }
        }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginScreen,
                R.id.registerScreen,
                R.id.splashScreen,
                R.id.detailScreen    -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

    }


}