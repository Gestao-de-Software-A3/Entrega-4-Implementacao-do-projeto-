package com.example.basket.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//Classe de dados (data class) que representa a entidade User no banco de dados.
@Entity
data class User(
    @PrimaryKey
    val id: String,
    val name: String,
    val password: String
)
