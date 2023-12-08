package com.example.basket.data.repository

import com.example.basket.data.database.dao.UserDao
import com.example.basket.domain.model.User
import com.example.basket.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

/**
 * Implementação concreta da interface UserRepository.
 * Responsável por fornecer métodos para interação com a camada de dados (DAO) relacionada a usuários.
 */
class UserRepositoryImpl(
    private val userDao: UserDao,
) : UserRepository {
    
    //Autentica um usuário com base no ID e senha fornecidos.
    override suspend fun authenticateUser(user: String, password: String): User? {
        return userDao.userLogin(user, password)
    }
    
    //Cria um novo usuário no banco de dados.
    override suspend fun createUser(user: User) {
        userDao.addUser(user)
    }
    
    //Obtém todos os usuários do banco de dados como um fluxo observável.
    override fun getAllUsers(): Flow<List<User>> {
        return userDao.searchAllUsers()
    }
    
    //Obtém um usuário pelo ID como um fluxo observável.
    override fun getUserById(userId: String): Flow<User?> {
        return userDao.searchUserById(userId)
    }
}