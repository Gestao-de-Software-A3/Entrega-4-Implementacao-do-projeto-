package com.example.basket.data.repository

import com.example.basket.data.database.dao.ProductDao
import com.example.basket.domain.model.Product
import com.example.basket.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(private val productDao: ProductDao) : ProductRepository {
    
    override suspend fun addProduct(product: Product) {
        productDao.add(product)
    }
    
    override suspend fun deleteProduct(product: Product) {
        productDao.delete(product)
    }
    
    override fun getAllProducts(): Flow<List<Product>> {
        return productDao.searchAll()
    }
    
    override fun findProductById(id: Long): Flow<Product?> {
        return productDao.findProductById(id)
    }
    
    override fun findUserProducts(userId: String): Flow<List<Product>> {
        return productDao.findUserProducts(userId)
    }
}