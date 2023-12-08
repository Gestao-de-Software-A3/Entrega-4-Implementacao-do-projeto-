package com.example.basket.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.example.basket.core.toActivity
import com.example.basket.data.preferences.dataStore
import com.example.basket.data.preferences.userPreferences
import com.example.basket.domain.model.User
import com.example.basket.domain.usecases.UserUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * Classe base para atividades relacionadas a usuários.
 * Fornece funcionalidades comuns, como verificação de usuário logado e acesso aos casos de uso relacionados a usuários.
 *
 * @property userUseCases Instâncias dos casos de uso relacionados a usuários.
 * @property _user Fluxo mutável para representar o usuário atualmente logado.
 * @property user Fluxo de leitura apenas para representar o usuário atualmente logado.
 */
open class BaseUserActivity : AppCompatActivity() {
    
    // Instâncias dos casos de uso relacionados a usuários
    private val userUseCases: UserUseCases by inject()
    
    // Fluxo mutável para representar o usuário atualmente logado
    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    
    // Fluxo de leitura apenas para representar o usuário atualmente logado
    protected val user: StateFlow<User?> = _user
    
    /**
     * Chamado quando a atividade está sendo criada.
     * Inicia a verificação do usuário logado durante o ciclo de vida da atividade.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            checkForLoggedUser()
        }
    }
    
    /**
     * Verifica se há um usuário logado ao iniciar a atividade.
     * Se houver, carrega as informações do usuário. Caso contrário, redireciona para a tela de login.
     */
    private suspend fun checkForLoggedUser() {
        dataStore.data.collect { preferences ->
            preferences[userPreferences]?.let { userId ->
                searchUser(userId)
            } ?: toLogin()
        }
    }
    
    /**
     * Busca informações do usuário com base no ID do usuário.
     *
     * @param userId O ID do usuário a ser procurado.
     * @return O usuário encontrado ou nulo se não for encontrado.
     */
    private suspend fun searchUser(userId: String): User? {
        return userUseCases
            .getUserByIdUseCase(userId)
            .firstOrNull().also {
                _user.value = it
            }
    }
    
    /**
     * Redireciona para a tela de login, removendo qualquer usuário logado.
     */
    private fun toLogin() {
        toActivity(LoginActivity::class.java) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        finish()
    }
    
    /**
     * Realiza o logout do usuário, removendo as preferências de usuário logado.
     */
    protected suspend fun logOut() {
        dataStore.edit { preferences ->
            preferences.remove(userPreferences)
        }
    }
    
    /**
     * Obtém a lista de todos os usuários.
     *
     * @return Fluxo contendo a lista de todos os usuários.
     */
    protected fun allUsers() = userUseCases.getAllUsersUseCase
}