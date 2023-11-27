package com.example.basket.data.repository

import com.example.basket.data.database.dao.UserDao
import com.example.basket.domain.model.User
import com.example.basket.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userDao: UserDao,
) : UserRepository {
    
    override suspend fun authenticateUser(user: String, password: String): User? {
        return userDao.userLogin(user, password)
    }
    
    override suspend fun createUser(user: User) {
        userDao.addUser(user)
    }
    
    override fun getAllUsers(): Flow<List<User>> {
        return userDao.searchAllUsers()
    }
    
    override fun getUserById(userId: String): Flow<User?> {
        return userDao.searchUserById(userId)
    }
}