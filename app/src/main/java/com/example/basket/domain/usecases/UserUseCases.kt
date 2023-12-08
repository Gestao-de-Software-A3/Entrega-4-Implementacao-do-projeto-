package com.example.basket.domain.usecases

import com.example.basket.domain.usecases.userUseCases.AddUserUseCase
import com.example.basket.domain.usecases.userUseCases.AuthenticateUserUseCase
import com.example.basket.domain.usecases.userUseCases.GetUserByIdUseCase
import com.example.basket.domain.usecases.userUseCases.GetAllUsersUseCase

/**
 * Classe de dados (data class) que contém instâncias dos casos de uso relacionados a usuários.
 *
 * @param authenticateUserUseCase Caso de uso para autenticar um usuário.
 * @param addUserUseCase Caso de uso para adicionar um novo usuário.
 * @param getAllUsersUseCase Caso de uso para obter todos os usuários.
 * @param getUserByIdUseCase Caso de uso para obter um usuário pelo ID.
 */
data class UserUseCases(
    val authenticateUserUseCase: AuthenticateUserUseCase,
    val addUserUseCase: AddUserUseCase,
    val getAllUsersUseCase: GetAllUsersUseCase,
    val getUserByIdUseCase: GetUserByIdUseCase
)