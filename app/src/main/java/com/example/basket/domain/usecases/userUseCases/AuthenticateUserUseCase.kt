package com.example.basket.domain.usecases.userUseCases

import com.example.basket.domain.model.User
import com.example.basket.domain.repository.UserRepository

class AuthenticateUserUseCase(private val userRepository: UserRepository) {
    
    suspend operator fun invoke(user: String, password: String): User? {
        return userRepository.authenticateUser(user, password)
    }
}