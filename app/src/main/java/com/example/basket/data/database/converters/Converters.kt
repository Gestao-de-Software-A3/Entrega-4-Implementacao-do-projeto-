package com.example.basket.data.database.converters

import androidx.room.TypeConverter
import java.math.BigDecimal

/**
 * Classe que contém métodos de conversão utilizados pelo Room para converter tipos específicos
 * para tipos suportados pelo banco de dados.
 */

class Converters {
    
    @TypeConverter
    fun fromDouble(price: Double?) : BigDecimal {
        return price?.let { BigDecimal(price.toString()) } ?: BigDecimal.ZERO
    }
    
    @TypeConverter
    fun bigDecimalToDouble(price: BigDecimal?) : Double? {
        return price?.let {price.toDouble()}
    }
}