package com.example.basket.data.repository

import com.example.basket.data.database.dao.ProductDao
import com.example.basket.domain.model.Product
import com.example.basket.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
/**
 * Implementação concreta da interface ProductRepository.
 * Responsável por fornecer métodos para interação com a camada de dados (DAO) relacionada a produtos.
 */
class ProductRepositoryImpl(private val productDao: ProductDao) : ProductRepository {
    
    // Adiciona um novo produto ao banco de dados.
    override suspend fun addProduct(product: Product) {
        productDao.add(product)
    }
    
    //Exclui um produto do banco de dados.
    override suspend fun deleteProduct(product: Product) {
        productDao.delete(product)
    }
    
    //Obtém todos os produtos do banco de dados como um fluxo observável.
    override fun getAllProducts(): Flow<List<Product>> {
        return productDao.searchAll()
    }
    
    //Encontra um produto pelo ID no banco de dados como um fluxo observável.
    override fun findProductById(id: Long): Flow<Product?> {
        return productDao.findProductById(id)
    }
    
    //Encontra todos os produtos associados a um usuário pelo ID do usuário como um fluxo observável.
    override fun findUserProducts(userId: String): Flow<List<Product>> {
        return productDao.findUserProducts(userId)
    }
}