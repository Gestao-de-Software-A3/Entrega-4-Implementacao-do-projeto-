package com.example.basket.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.example.basket.core.toActivity
import com.example.basket.core.toHash
import com.example.basket.core.toast
import com.example.basket.data.preferences.dataStore
import com.example.basket.data.preferences.userPreferences
import com.example.basket.databinding.LoginActivityBinding
import com.example.basket.domain.usecases.UserUseCases
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    
    //(private val userUseCases: UserUseCases)
    private val userUseCases: UserUseCases by inject()
    
    private val binding by lazy {
        LoginActivityBinding.inflate(layoutInflater)
    }
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRegisterButton()
        setLoginButton()
    }
    
    private fun setLoginButton() {
        binding.loginButton.setOnClickListener {
            val usuario = binding.userField.text.toString()
            val senha = binding.passwordField.text.toString().toHash()
            authenticateUser(usuario, senha)
        }
    }
    
    private fun authenticateUser(user: String, password: String) {
        lifecycleScope.launch {
            userUseCases.authenticateUserUseCase(user, password)?.let { user ->
                dataStore.edit { preferences ->
                    preferences[userPreferences] = user.id
                }
                toActivity(ProductListActivity::class.java)
                finish()
            } ?: toast("Falha na autenticação")
        }
    }
    
    private fun setRegisterButton() {
        binding.registerButton.setOnClickListener {
            toActivity(UserFormActivity::class.java)
        }
    }
}