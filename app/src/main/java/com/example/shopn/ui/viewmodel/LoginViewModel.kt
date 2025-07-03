package com.example.shopn.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopn.data.repo.ShopRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(var shopRepository: ShopRepository) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    private val _resetPasswordStatus = MutableLiveData<Result<String>>()
    val resetPasswordStatus: LiveData<Result<String>> = _resetPasswordStatus

    fun loginWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _loginStatus.value = task.isSuccessful
            }
    }

    fun loginWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                _loginStatus.value = task.isSuccessful
            }
    }

    fun resetPassword(email: String) {
        if (email.isBlank()) {
            _resetPasswordStatus.value = Result.failure(Exception("Lütfen geçerli bir e-posta giriniz"))
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                _resetPasswordStatus.value = Result.success("Şifre sıfırlama e-postası gönderildi")
            }
            .addOnFailureListener {
                _resetPasswordStatus.value = Result.failure(it)
            }
    }


}