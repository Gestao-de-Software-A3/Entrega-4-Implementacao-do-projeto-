package com.example.basket.domain.usecases.userUseCases

import com.example.basket.domain.model.User
import com.example.basket.domain.repository.UserRepository

class AddUserUseCase(private val userRepository: UserRepository) {
    
    suspend operator fun invoke(user: User){
        userRepository.createUser(user)
    }
}