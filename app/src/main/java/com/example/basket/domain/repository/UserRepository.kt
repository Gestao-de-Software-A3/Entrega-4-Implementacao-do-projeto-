package com.example.basket.domain.repository

import com.example.basket.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    
    suspend fun authenticateUser(user: String, password: String): User?
    suspend fun createUser(user: User)
    fun getAllUsers(): Flow<List<User>>
    fun getUserById(userId: String): Flow<User?>
}