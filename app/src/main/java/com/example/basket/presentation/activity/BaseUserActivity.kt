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

open class BaseUserActivity : AppCompatActivity() {
    
    
    //(private val userUseCases: UserUseCases)
    private val userUseCases: UserUseCases by inject()
    
    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    protected val user: StateFlow<User?> = _user
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            checkForLoggedUser()
        }
    }
    
    private suspend fun checkForLoggedUser() {
        dataStore.data.collect() { preferences ->
            preferences[userPreferences]?.let { userId ->
                searchUser(userId)
            } ?: toLogin()
        }
    }
    
    private suspend fun searchUser(userId: String): User? {
        return userUseCases
            .getUserByIdUseCase(userId)
            .firstOrNull().also {
                _user.value = it
            }
    }
    
    private fun toLogin() {
        toActivity(LoginActivity::class.java) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        finish()
    }
    
    protected suspend fun logOut() {
        dataStore.edit { preferences ->
            preferences.remove(userPreferences)
        }
    }
    
    protected fun allUsers() = userUseCases.getAllUsersUseCase
    
}