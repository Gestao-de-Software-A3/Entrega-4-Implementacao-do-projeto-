package com.example.basket.di

import com.example.basket.domain.usecases.ProductUseCases
import com.example.basket.domain.usecases.UserUseCases
import com.example.basket.domain.usecases.productUseCases.AddProductUseCase
import com.example.basket.domain.usecases.productUseCases.DeleteProductUseCase
import com.example.basket.domain.usecases.productUseCases.FindProductByIdUseCase
import com.example.basket.domain.usecases.productUseCases.FindUserProductsUseCase
import com.example.basket.domain.usecases.productUseCases.GetAllProductsUseCase
import com.example.basket.domain.usecases.userUseCases.AddUserUseCase
import com.example.basket.domain.usecases.userUseCases.AuthenticateUserUseCase
import com.example.basket.domain.usecases.userUseCases.GetAllUsersUseCase
import com.example.basket.domain.usecases.userUseCases.GetUserByIdUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {
    
    fun load() {
        loadKoinModules(useCasesModule())
    }
    
    private fun useCasesModule(): Module {
        return module {
            factory { AuthenticateUserUseCase(userRepository = get()) }
            factory { AddUserUseCase(userRepository = get()) }
            factory { GetUserByIdUseCase(userRepository = get()) }
            factory { GetAllUsersUseCase(userRepository = get()) }
            factory { UserUseCases(
                authenticateUserUseCase = get(),
                addUserUseCase = get(),
                getUserByIdUseCase = get(),
                getAllUsersUseCase = get()
            ) }
            factory { AddProductUseCase(productRepository = get()) }
            factory { DeleteProductUseCase(productRepository = get()) }
            factory { FindProductByIdUseCase(productRepository = get()) }
            factory { FindUserProductsUseCase(productRepository = get()) }
            factory { GetAllProductsUseCase(productRepository = get()) }
            factory { ProductUseCases(
                addProductUseCase = get(),
                deleteProductUseCase = get(),
                findProductByIdUseCase = get(),
                findUserProductsUseCase = get(),
                getAllProductsUseCase = get(),
            ) }
        }
    }
}