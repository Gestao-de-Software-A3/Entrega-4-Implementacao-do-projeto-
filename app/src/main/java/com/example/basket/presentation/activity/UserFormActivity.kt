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

/**
 * Atividade responsável pelo formulário de registro de novos usuários.
 *
 * @property userUseCases Instâncias dos casos de uso relacionados a usuários.
 * @property binding Objeto de ligação para a interface do usuário usando View Binding.
 */
class UserFormActivity : AppCompatActivity() {
    
    // Instâncias dos casos de uso relacionados a usuários
    private val userUseCases: UserUseCases by inject()
    
    // Objeto de ligação para a interface do usuário usando View Binding
    private val binding by lazy {
        UserFormActivityBinding.inflate(layoutInflater)
    }
    
    /**
     * Chamado quando a atividade está sendo criada.
     * Configura a interface do usuário, incluindo o botão de registro e sua lógica associada.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRegisterButton()
    }
    
    /**
     * Configura o botão de registro e sua ação associada.
     */
    private fun setRegisterButton() {
        binding.formRegisterButton.setOnClickListener {
            val newUser = createUser()
            register(newUser)
        }
    }
    
    /**
     * Executa o processo de registro do novo usuário.
     *
     * @param user Novo usuário a ser registrado.
     */
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
    
    /**
     * Cria uma instância de usuário com base nos campos do formulário.
     *
     * @return Nova instância de usuário.
     */
    private fun createUser(): User {
        val userId = binding.formUserField.text.toString()
        val name = binding.formUserNameField.text.toString()
        val password = binding.formPasswordField.text.toString().toHash()
        return User(userId, name, password)
    }
}