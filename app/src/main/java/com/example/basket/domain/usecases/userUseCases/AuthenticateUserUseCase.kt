package com.example.basket.domain.usecases.userUseCases

import com.example.basket.domain.model.User
import com.example.basket.domain.repository.UserRepository

//Caso de uso (Use Case) responsável por autenticar um usuário.
class AuthenticateUserUseCase(private val userRepository: UserRepository) {
    
    suspend operator fun invoke(user: String, password: String): User? {
        return userRepository.authenticateUser(user, password)
    }
}