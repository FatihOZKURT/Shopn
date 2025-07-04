package com.example.shopn.ui.screen

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shopn.databinding.RegisterScreenBinding
import com.example.shopn.ui.viewmodel.RegisterViewModel
import com.example.shopn.util.Constants.WEB_CLIENT_ID
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import com.example.shopn.R
import androidx.core.content.edit

@AndroidEntryPoint
class RegisterScreen : Fragment() {

    private lateinit var binding: RegisterScreenBinding
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                viewModel.registerWithGoogle(idToken)
            }
        } catch (e: Exception) {
            Log.e("RegisterScreen", "Google sign-up failed", e)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegisterScreenBinding.inflate(inflater, container, false)

        setupGoogleLogin()

        binding.createAccount.setOnClickListener {
            val email = binding.emailTextRegister.text.toString()
            val password = binding.passwordTextRegister.text.toString()
            val confirm = binding.confirmPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Email ve şifre boş olamaz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirm) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.registerWithEmail(email, password)
        }

        binding.imageButtonSignUpWithGoogle.setOnClickListener {
            googleSignInLauncher.launch(googleSignInClient.signInIntent)
        }

        binding.goToLoginText.setOnClickListener {
            findNavController().navigateUp()
        }

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.registerStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                val email = FirebaseAuth.getInstance().currentUser?.email ?: ""
                saveEmailToPrefs(email)
                Toast.makeText(requireContext(), "Kayıt Başarılı", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerScreen_to_mainScreen)
            } else {
                Toast.makeText(requireContext(), "Kayıt Başarısız", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveEmailToPrefs(email: String) {
        val prefs = requireContext().getSharedPreferences("user_info", Context.MODE_PRIVATE)
        prefs.edit { putString("email", email) }
    }

    private fun setupGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WEB_CLIENT_ID)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }
}
