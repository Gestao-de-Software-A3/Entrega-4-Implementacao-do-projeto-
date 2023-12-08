package com.example.basket.domain.usecases.userUseCases

import com.example.basket.domain.model.User
import com.example.basket.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

//Caso de uso (Use Case) responsável por obter um usuário pelo ID.
class GetUserByIdUseCase(private val userRepository: UserRepository) {
    
    operator fun invoke(userId: String): Flow<User?> {
        return userRepository.getUserById(userId)
    }
}