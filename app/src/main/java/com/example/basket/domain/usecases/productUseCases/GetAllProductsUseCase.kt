package com.example.basket.domain.usecases.productUseCases

import com.example.basket.domain.model.Product
import com.example.basket.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetAllProductsUseCase(private val productRepository: ProductRepository) {
    fun invoke(): Flow<List<Product>> {
        return productRepository.getAllProducts()
    }
}