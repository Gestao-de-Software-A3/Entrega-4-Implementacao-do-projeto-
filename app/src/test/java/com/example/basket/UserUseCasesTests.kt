package com.example.basket

import com.example.basket.domain.model.User
import com.example.basket.domain.repository.UserRepository
import com.example.basket.domain.usecases.userUseCases.AddUserUseCase
import com.example.basket.domain.usecases.userUseCases.AuthenticateUserUseCase
import com.example.basket.domain.usecases.userUseCases.GetAllUsersUseCase
import com.example.basket.domain.usecases.userUseCases.GetUserByIdUseCase
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

class UserUseCasesTests {
    private lateinit var addUserUseCase: AddUserUseCase
    private lateinit var authenticateUserUseCase: AuthenticateUserUseCase
    private lateinit var getAllUsersUseCase: GetAllUsersUseCase
    private lateinit var getUserByIdUseCase: GetUserByIdUseCase
    private lateinit var userRepository: UserRepository
    
    @Before
    fun setUp() {
        userRepository = mockk()
        addUserUseCase = AddUserUseCase(userRepository)
        authenticateUserUseCase = AuthenticateUserUseCase(userRepository)
        getAllUsersUseCase = GetAllUsersUseCase(userRepository)
        getUserByIdUseCase = GetUserByIdUseCase(userRepository)
    }
    
    @Test
    fun invokeShouldCallCreateUserInRepository() = runBlocking {
        val user = mockk<User>()
        
        coEvery { userRepository.createUser(user) } returns Unit
        
        addUserUseCase(user)
        
        coVerify(exactly = 1) { userRepository.createUser(user) }
    }
    
    @Test
    fun invokeShouldReturnUserFromRepository() = runBlocking {
        val user = mockk<User>()
        val userString = "user123"
        val password = "password123"
        
        coEvery { userRepository.authenticateUser(userString, password) } returns user
        
        val result = authenticateUserUseCase(userString, password)
        
        assertEquals(user, result)
        coVerify(exactly = 1) { userRepository.authenticateUser(userString, password) }
    }
    
    @Test
    fun invokeShouldReturnFlowOfAllUsersFromRepository() = runBlocking {
        val usersFlow = flowOf<List<User>>(emptyList())
        
        every { userRepository.getAllUsers() } returns usersFlow
        
        val result = getAllUsersUseCase.invoke()
        
        assertEquals(usersFlow, result)
        verify(exactly = 1) { userRepository.getAllUsers() }
    }
    
    @Test
    fun invokeShouldReturnFlowOfUserByIdFromRepository() = runBlocking {
        val userId = "user123"
        val userFlow = flowOf<User?>(mockk())
        
        every { userRepository.getUserById(userId) } returns userFlow
        
        val result = getUserByIdUseCase.invoke(userId)
        
        assertEquals(userFlow, result)
        verify(exactly = 1) { userRepository.getUserById(userId) }
    }
}