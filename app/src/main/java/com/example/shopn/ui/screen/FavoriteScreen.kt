package com.example.shopn.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shopn.R
import com.example.shopn.databinding.FavoriteScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteScreen : Fragment() {

    private lateinit var binding: FavoriteScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoriteScreenBinding.inflate(inflater, container, false)

        return binding.root
    }


}