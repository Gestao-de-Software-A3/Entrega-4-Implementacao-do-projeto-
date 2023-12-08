package com.example.basket.domain.usecases.productUseCases

import com.example.basket.domain.model.Product
import com.example.basket.domain.repository.ProductRepository

//Caso de uso (Use Case) responsável por excluir um produto existente.
class DeleteProductUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(product: Product) {
        productRepository.deleteProduct(product)
    }
}