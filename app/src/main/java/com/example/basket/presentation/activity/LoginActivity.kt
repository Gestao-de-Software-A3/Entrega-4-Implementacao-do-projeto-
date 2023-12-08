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

/**
 * Atividade responsável pela tela de login do aplicativo.
 *
 * @property userUseCases Instâncias dos casos de uso relacionados a usuários.
 * @property binding Objeto de ligação para a interface do usuário usando View Binding.
 */
class LoginActivity : AppCompatActivity() {
    
    // Instâncias dos casos de uso relacionados a usuários
    private val userUseCases: UserUseCases by inject()
    
    // Objeto de ligação para a interface do usuário usando View Binding
    private val binding by lazy {
        LoginActivityBinding.inflate(layoutInflater)
    }
    
    /**
     * Chamado quando a atividade está sendo criada.
     * Configura a interface do usuário e os ouvintes de botões.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRegisterButton()
        setLoginButton()
    }
    
    /**
     * Configura o ouvinte de botão para o botão de login.
     * Autentica o usuário com base nas informações fornecidas e redireciona para a tela de lista de produtos em caso de sucesso.
     */
    private fun setLoginButton() {
        binding.loginButton.setOnClickListener {
            val user = binding.userField.text.toString()
            val password = binding.passwordField.text.toString().toHash()
            authenticateUser(user, password)
        }
    }
    
    /**
     * Autentica o usuário com base no ID e senha fornecidos.
     * Em caso de sucesso, armazena o ID do usuário nas preferências e redireciona para a tela de lista de produtos.
     * Em caso de falha, exibe uma mensagem de falha de autenticação.
     *
     * @param user O ID do usuário.
     * @param password A senha do usuário.
     */
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
    
    /**
     * Configura o ouvinte de botão para o botão de registro.
     * Redireciona para a tela de formulário de usuário ao ser clicado.
     */
    private fun setRegisterButton() {
        binding.registerButton.setOnClickListener {
            toActivity(UserFormActivity::class.java)
        }
    }
}