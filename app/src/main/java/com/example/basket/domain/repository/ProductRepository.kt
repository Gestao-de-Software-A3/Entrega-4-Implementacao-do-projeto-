package com.example.basket.domain.repository

import com.example.basket.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun addProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    fun getAllProducts(): Flow<List<Product>>
    fun findProductById(id: Long): Flow<Product?>
    fun findUserProducts(userId: String): Flow<List<Product>>
}