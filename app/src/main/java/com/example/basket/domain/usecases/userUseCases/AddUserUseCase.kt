package com.example.basket.domain.usecases.userUseCases

import com.example.basket.domain.model.User
import com.example.basket.domain.repository.UserRepository

//Caso de uso (Use Case) responsável por adicionar um novo usuário.
class AddUserUseCase(private val userRepository: UserRepository) {
    
    suspend operator fun invoke(user: User){
        userRepository.createUser(user)
    }
}