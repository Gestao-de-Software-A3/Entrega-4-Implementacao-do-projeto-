package com.example.basket.domain.repository

import com.example.basket.domain.model.Product
import kotlinx.coroutines.flow.Flow

/**
 * Interface que define os métodos para interação com a camada de dados (DAO) relacionada a produtos.
 */
interface ProductRepository {
    //Adiciona um novo produto ao banco de dados.
    suspend fun addProduct(product: Product)
    //Exclui um produto do banco de dados.
    suspend fun deleteProduct(product: Product)
    //Obtém todos os produtos do banco de dados como um fluxo observável.
    fun getAllProducts(): Flow<List<Product>>
    //Encontra um produto pelo ID no banco de dados como um fluxo observável.
    fun findProductById(id: Long): Flow<Product?>
    //Encontra todos os produtos associados a um usuário pelo ID do usuário como um fluxo observável.
    fun findUserProducts(userId: String): Flow<List<Product>>
}