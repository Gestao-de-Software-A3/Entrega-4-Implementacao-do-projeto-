package com.example.basket.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.basket.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Interface que define métodos de acesso a dados (DAO - Data Access Object) para a entidade User,
 * fornecendo métodos abstratos para realizar operações CRUD (Create, Read, Update, Delete)
 * no banco de dados relacionado à entidade User.
 *
 * - @Insert: Método para inserir um usuário no banco de dados.
 * - @Query: Métodos para consultar o banco de dados em busca de usuários por ID, por ID e senha,
 *   ou para obter a lista de todos os usuários. As funções que retornam Flow são usadas para
 *   permitir observação dos resultados, atualizando automaticamente a UI quando os dados mudam.
 */

@Dao
interface UserDao {
    
    @Insert
    suspend fun addUser(user: User)
    
    @Query("SELECT * FROM User WHERE id = :userId AND password = :password")
    suspend fun userLogin(
        userId: String,
        password: String
    ): User?
    
    @Query("SELECT * FROM User WHERE id = :userId")
    fun searchUserById(userId: String): Flow<User>
    
    @Query("SELECT * FROM User")
    fun searchAllUsers(): Flow<List<User>>
}