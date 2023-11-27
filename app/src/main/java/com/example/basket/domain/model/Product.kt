package com.example.basket.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity
@Parcelize
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val image: String? = null,
    val userId: String? = null
) : Parcelable  {
    fun savedWithNoUser() = userId.isNullOrBlank() && id > 0L
}
