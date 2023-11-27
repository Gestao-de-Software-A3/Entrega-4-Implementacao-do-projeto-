package com.example.basket

import com.example.basket.data.database.dao.UserDao
import com.example.basket.data.repository.UserRepositoryImpl
import com.example.basket.domain.model.User
import com.example.basket.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTests {
    
    private lateinit var userRepository: UserRepository
    private lateinit var userDao: UserDao
    
    @Before
    fun setUp() {
        userDao = mockk()
        userRepository = UserRepositoryImpl(userDao)
    }
    
    @Test
    fun testAuthenticateUserShouldReturnUserFromDao() = runBlocking {
        val username = "mario"
        val password = "secure_password"
        val user = mockk<User>()
        
        coEvery { userDao.userLogin(username, password) } returns user
        
        val result = userRepository.authenticateUser(username, password)
        
        assertEquals(user, result)
        coVerify(exactly = 1) { userDao.userLogin(username, password) }
    }
    
    @Test
    fun testCreateUserShouldCallAddUserInDao() = runBlocking {
        val user = mockk<User>()
        
        coEvery { userDao.addUser(user) } returns Unit
        
        userRepository.createUser(user)
        
        coVerify(exactly = 1) { userDao.addUser(user) }
    }
    
    @Test
    fun testGetAllUsersShouldReturnFlowFromDao() = runBlocking {
        val usersFlow = flowOf<List<User>>(emptyList())
        
        every { userDao.searchAllUsers() } returns usersFlow
        
        val result = userRepository.getAllUsers()
        
        assertEquals(usersFlow, result)
        verify(exactly = 1) { userDao.searchAllUsers() }
    }
    
    @Test
    fun testGetUserByIdShouldReturnFlowFromDao() = runBlocking {
        val userId = "user123"
        val userFlow = flowOf<User>(mockk())
        
        every { userDao.searchUserById(userId) } returns userFlow
        
        val result = userRepository.getUserById(userId)
        
        assertEquals(userFlow, result)
        verify(exactly = 1) { userDao.searchUserById(userId) }
    }
}