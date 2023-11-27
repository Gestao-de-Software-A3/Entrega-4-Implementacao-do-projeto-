package com.example.basket.domain.usecases.productUseCases

import com.example.basket.domain.model.Product
import com.example.basket.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class FindUserProductsUseCase(private val productRepository: ProductRepository) {
    operator fun invoke(userId: String): Flow<List<Product>> {
        return productRepository.findUserProducts(userId)
    }
}