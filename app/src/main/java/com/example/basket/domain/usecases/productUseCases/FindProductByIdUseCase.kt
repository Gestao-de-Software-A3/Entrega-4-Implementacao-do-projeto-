package com.example.basket.domain.usecases.productUseCases

import com.example.basket.domain.model.Product
import com.example.basket.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

//Caso de uso (Use Case) responsável por encontrar um produto pelo ID.
class FindProductByIdUseCase(private val productRepository: ProductRepository) {
    operator fun invoke(id: Long): Flow<Product?> {
        return productRepository.findProductById(id)
    }
}
