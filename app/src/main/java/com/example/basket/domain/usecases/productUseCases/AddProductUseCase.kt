package com.example.basket.domain.usecases.productUseCases

import com.example.basket.domain.model.Product
import com.example.basket.domain.repository.ProductRepository

class AddProductUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(product: Product) {
        productRepository.addProduct(product)
    }
}