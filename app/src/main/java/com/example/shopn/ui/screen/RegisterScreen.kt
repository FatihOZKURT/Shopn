package com.example.shopn.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shopn.databinding.RegisterScreenBinding
import com.example.shopn.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class RegisterScreen : Fragment() {

    private lateinit var binding: RegisterScreenBinding
    private lateinit var viewModel : RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val tempViewModel : RegisterViewModel by viewModels()
//        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterScreenBinding.inflate(inflater, container, false)

        binding.goToLoginText.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }


}