package com.example.basket.domain.usecases.userUseCases

import com.example.basket.domain.model.User
import com.example.basket.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

//Caso de uso (Use Case) responsável por obter todos os usuários.
class GetAllUsersUseCase(private val userRepository: UserRepository) {
    
    operator fun invoke(): Flow<List<User>> {
        return userRepository.getAllUsers()
    }
}