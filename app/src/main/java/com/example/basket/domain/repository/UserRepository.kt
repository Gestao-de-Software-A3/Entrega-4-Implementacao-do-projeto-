package com.example.basket.domain.repository

import com.example.basket.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Interface que define os métodos para interação com a camada de dados (DAO) relacionada a usuários.
 */
interface UserRepository {
    //Autentica um usuário com base no ID e senha fornecidos.
    suspend fun authenticateUser(user: String, password: String): User?
    //Cria um novo usuário no banco de dados.
    suspend fun createUser(user: User)
    //Obtém todos os usuários do banco de dados como um fluxo observável.
    fun getAllUsers(): Flow<List<User>>
    //Obtém um usuário pelo ID como um fluxo observável.
    fun getUserById(userId: String): Flow<User?>
}