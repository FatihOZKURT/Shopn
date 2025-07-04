package com.example.shopn.ui.screen


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shopn.R
import com.example.shopn.databinding.LoginScreenBinding
import com.example.shopn.ui.viewmodel.LoginViewModel
import com.example.shopn.util.Constants.WEB_CLIENT_ID
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.content.edit

@AndroidEntryPoint
class LoginScreen : Fragment() {

    private lateinit var binding: LoginScreenBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                viewModel.loginWithGoogle(idToken)
            }
        } catch (e: ApiException) {
            Log.e("LoginScreen", "Google sign in failed", e)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginScreenBinding.inflate(inflater, container, false)

        setupGoogleLogin()

        binding.signIn.setOnClickListener {
            val email = binding.emailTextLogin.text.toString()
            val password = binding.passwordTextLogin.text.toString()
            viewModel.loginWithEmail(email, password)
        }

        binding.imageButtonSignInWithGoogle.setOnClickListener {
            googleSignInLauncher.launch(googleSignInClient.signInIntent)
        }

        viewModel.loginStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                val email = FirebaseAuth.getInstance().currentUser?.email ?: ""
                saveEmailToPrefs(email)

                Toast.makeText(requireContext(), "Hoşgeldiniz, keyifli alışverişler", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginScreen_to_mainScreen)
            } else {
                Toast.makeText(requireContext(), "Giriş Başarısız", Toast.LENGTH_SHORT).show()
            }
        }

        binding.goToRegisterText.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreen_to_registerScreen)
        }

        binding.forgotPassword.setOnClickListener {
            val email = binding.emailTextLogin.text.toString().trim()
            viewModel.resetPassword(email)
        }

        viewModel.resetPasswordStatus.observe(viewLifecycleOwner) { result ->
            result
                .onSuccess {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                .onFailure {
                    Toast.makeText(requireContext(), "Hata: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {}
    }



    private fun setupGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WEB_CLIENT_ID)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun saveEmailToPrefs(email: String) {
        val prefs = requireContext().getSharedPreferences("user_info", Context.MODE_PRIVATE)
        prefs.edit { putString("email", email) }
    }
}
