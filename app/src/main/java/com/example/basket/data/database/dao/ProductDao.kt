package com.example.basket.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.basket.domain.model.Product
import kotlinx.coroutines.flow.Flow

/**
 * Interface que define métodos de acesso a dados (DAO - Data Access Object) para a entidade Product,
 *  * fornecendo métodos abstratos para realizar operações CRUD (Create, Read, Update, Delete)
 * no banco de dados relacionado à entidade Product.
 *
 * - @Query: Método para buscar todos os produtos no banco de dados. Retorna um Flow observável
 *   que emite a lista de produtos sempre que ela é modificada.
 *
 * - @Insert: Método para inserir um produto no banco de dados. A anotação @onConflict é usada para
 *   especificar a estratégia de conflito, REPLACE neste caso, o que significa que, se houver um
 *   conflito de chave primária, o produto existente será substituído pelo novo.
 *
 * - @Delete: Método para excluir um produto do banco de dados.
 *
 * - @Query: Método para encontrar um produto pelo ID no banco de dados. Retorna um Flow observável
 *   que emite o produto sempre que ele é modificado.
 *
 * - @Query: Método para encontrar todos os produtos associados a um determinado usuário pelo ID do usuário.
 *   Retorna um Flow observável que emite a lista de produtos sempre que ela é modificada.
 */

@Dao
interface ProductDao {
    
    @Query("SELECT * FROM Product")
    fun searchAll(): Flow<List<Product>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(product: Product)
    
    @Delete
    suspend fun delete(product: Product)
    
    @Query("SELECT * FROM Product WHERE id = :id")
    fun findProductById(id: Long) : Flow<Product?>
    
    @Query("SELECT * FROM Product WHERE userId = :userId")
    fun findUserProducts(userId: String) : Flow<List<Product>>
    
}