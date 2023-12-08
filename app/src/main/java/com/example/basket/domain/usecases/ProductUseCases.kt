package com.example.basket.domain.usecases

import com.example.basket.domain.usecases.productUseCases.AddProductUseCase
import com.example.basket.domain.usecases.productUseCases.DeleteProductUseCase
import com.example.basket.domain.usecases.productUseCases.FindProductByIdUseCase
import com.example.basket.domain.usecases.productUseCases.FindUserProductsUseCase
import com.example.basket.domain.usecases.productUseCases.GetAllProductsUseCase

/**
 * Classe de dados (data class) que contém instâncias dos casos de uso relacionados a produtos.
 *
 * @param addProductUseCase Caso de uso para adicionar um novo produto.
 * @param deleteProductUseCase Caso de uso para excluir um produto existente.
 * @param findProductByIdUseCase Caso de uso para encontrar um produto pelo ID.
 * @param findUserProductsUseCase Caso de uso para encontrar todos os produtos associados a um usuário.
 * @param getAllProductsUseCase Caso de uso para obter todos os produtos.
 */
data class ProductUseCases(
    val addProductUseCase: AddProductUseCase,
    val deleteProductUseCase: DeleteProductUseCase,
    val findProductByIdUseCase: FindProductByIdUseCase,
    val findUserProductsUseCase: FindUserProductsUseCase,
    val getAllProductsUseCase: GetAllProductsUseCase
)
