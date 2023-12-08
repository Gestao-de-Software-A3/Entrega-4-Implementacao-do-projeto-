package com.example.basket.domain.usecases.productUseCases

import com.example.basket.domain.model.Product
import com.example.basket.domain.repository.ProductRepository

//Caso de uso (Use Case) responsável por adicionar um novo produto.
class AddProductUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(product: Product) {
        productRepository.addProduct(product)
    }
}