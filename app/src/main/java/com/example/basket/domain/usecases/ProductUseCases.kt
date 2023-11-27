package com.example.basket.domain.usecases

import com.example.basket.domain.usecases.productUseCases.AddProductUseCase
import com.example.basket.domain.usecases.productUseCases.DeleteProductUseCase
import com.example.basket.domain.usecases.productUseCases.FindProductByIdUseCase
import com.example.basket.domain.usecases.productUseCases.FindUserProductsUseCase
import com.example.basket.domain.usecases.productUseCases.GetAllProductsUseCase

data class ProductUseCases(
    val addProductUseCase: AddProductUseCase,
    val deleteProductUseCase: DeleteProductUseCase,
    val findProductByIdUseCase: FindProductByIdUseCase,
    val findUserProductsUseCase: FindUserProductsUseCase,
    val getAllProductsUseCase: GetAllProductsUseCase
)
