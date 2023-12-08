package com.example.basket.domain.usecases.productUseCases

import com.example.basket.domain.model.Product
import com.example.basket.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

//Caso de uso (Use Case) responsável por encontrar todos os produtos associados a um usuário.
class FindUserProductsUseCase(private val productRepository: ProductRepository) {
    operator fun invoke(userId: String): Flow<List<Product>> {
        return productRepository.findUserProducts(userId)
    }
}