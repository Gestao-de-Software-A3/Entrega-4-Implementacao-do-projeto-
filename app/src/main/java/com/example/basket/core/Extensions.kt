package com.example.basket.core

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.Toast
import coil.load
import com.example.basket.R
import java.math.BigDecimal
import java.security.MessageDigest
import java.text.NumberFormat
import java.util.Locale

fun Context.toActivity(clazz: Class<*>, intent: Intent.() -> Unit = {}) {
    Intent(this, clazz)
        .apply {
            intent()
            startActivity(this)
        }
}

fun String.toHash(
    code: String = "SHA-256"
): String {
    return MessageDigest
        .getInstance(code)
        .digest(this.toByteArray())
        .fold("", { str, byte ->
            str + "%02x".format(byte)
        })
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .show()
}

fun BigDecimal.formatToBrazilianCurrency(): String {
    
    val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
    return formatter.format(this)
}
fun ImageView.loadImage(url: String? = null, fallback: Int = R.drawable.placeholder) {
    
    load(url) {
        fallback(fallback)
        error(R.drawable.error)
        placeholder(R.drawable.placeholder)
        crossfade(1000)
    }
}
