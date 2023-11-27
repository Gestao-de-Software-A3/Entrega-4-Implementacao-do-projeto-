package com.example.basket.presentation.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.basket.core.loadImage
import com.example.basket.databinding.ImageFormBinding

class ImageFormDialog(private val context: Context) {
   
    fun showDialog(
        baseUrl: String? = null,
        loadedImage: (image: String) -> Unit
    ) {
        ImageFormBinding.inflate(LayoutInflater.from(context)).apply {
            baseUrl?.let {
                imageFormImageview.loadImage(it)
                imageFormUrl.setText(it)
            }
            imageFormLoadButton.setOnClickListener {
                val url = imageFormUrl.text.toString()
                imageFormImageview.loadImage(url)
            }
            
            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("Confirmar") { _, _ ->
                    val url = imageFormUrl.text.toString()
                    loadedImage(url)
                }
                .setNegativeButton("Cancelar") { _, _ -> }
                .show()
        }
    }
}