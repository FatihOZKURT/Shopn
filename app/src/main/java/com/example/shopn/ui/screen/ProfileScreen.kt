package com.example.shopn.ui.screen

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shopn.R
import com.example.shopn.databinding.ProfileScreenBinding
import com.google.firebase.auth.FirebaseAuth


class ProfileScreen : Fragment() {

    private lateinit var binding: ProfileScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileScreenBinding.inflate(inflater, container, false)


        binding.buttonSignOut.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Çıkış Yap")
                .setMessage("Çıkış yapmak istediğinize emin misiniz?")
                .setPositiveButton("Evet") { _, _ ->
                    FirebaseAuth.getInstance().signOut()
                    val action = ProfileScreenDirections.actionProfileScreenToLoginScreen()
                    findNavController().navigate(action)
                }
                .setNegativeButton("İptal", null)
                .show()
        }



        return binding.root
    }


}