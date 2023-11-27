package com.example.basket.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.basket.core.toHash
import com.example.basket.core.toast
import com.example.basket.data.database.AppDatabase
import com.example.basket.databinding.UserFormActivityBinding
import com.example.basket.domain.model.User
import com.example.basket.domain.usecases.UserUseCases
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class UserFormActivity : AppCompatActivity() {
    
    //(private val userUseCases: UserUseCases)
    private val userUseCases: UserUseCases by inject()
    
    private val binding by lazy {
        UserFormActivityBinding.inflate(layoutInflater)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRegisterButton()
    }
    
    private fun setRegisterButton() {
        binding.formRegisterButton.setOnClickListener {
            val newUser = createUser()
            register(newUser)
        }
    }
    
    private fun register(user: User) {
        lifecycleScope.launch {
            try {
                userUseCases.addUserUseCase(user)
                finish()
            } catch (e: Exception) {
                toast("Falha ao cadastrar usuário")
            }
        }
    }
    
    private fun createUser(): User {
        val user = binding.formUserField.text.toString()
        val name = binding.formUserNameField.text.toString()
        val password = binding.formPasswordField.text.toString().toHash()
        return User(user, name, password)
    }
}