package com.example.basket.domain.usecases

import com.example.basket.domain.usecases.userUseCases.AddUserUseCase
import com.example.basket.domain.usecases.userUseCases.AuthenticateUserUseCase
import com.example.basket.domain.usecases.userUseCases.GetUserByIdUseCase
import com.example.basket.domain.usecases.userUseCases.GetAllUsersUseCase

data class UserUseCases(
    val authenticateUserUseCase: AuthenticateUserUseCase,
    val addUserUseCase: AddUserUseCase,
    val getAllUsersUseCase: GetAllUsersUseCase,
    val getUserByIdUseCase: GetUserByIdUseCase
) {
}