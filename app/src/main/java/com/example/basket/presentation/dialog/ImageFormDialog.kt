package com.example.basket.presentation.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.basket.core.loadImage
import com.example.basket.databinding.ImageFormBinding

/**
 * Diálogo para exibição e edição de URLs de imagens.
 *
 * @property context Contexto associado ao diálogo.
 */
class ImageFormDialog(private val context: Context) {
    
    /**
     * Exibe o diálogo para editar a URL de uma imagem.
     *
     * @param baseUrl URL base da imagem, se disponível.
     * @param loadedImage Ação a ser executada quando uma nova URL de imagem é confirmada.
     */
    fun showDialog(
        baseUrl: String? = null,
        loadedImage: (image: String) -> Unit
    ) {
        // Infla o layout do formulário de imagem usando View Binding
        ImageFormBinding.inflate(LayoutInflater.from(context)).apply {
            
            // Carrega a imagem e preenche a URL base, se disponível
            baseUrl?.let {
                imageFormImageview.loadImage(it)
                imageFormUrl.setText(it)
            }
            
            // Configura o botão de carregamento para carregar a imagem a partir da URL fornecida
            imageFormLoadButton.setOnClickListener {
                val url = imageFormUrl.text.toString()
                imageFormImageview.loadImage(url)
            }
            
            // Cria e exibe o AlertDialog com o layout do formulário de imagem
            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("Confirmar") { _, _ ->
                    // Ao confirmar, obtém a URL do campo de texto e executa a ação associada
                    val url = imageFormUrl.text.toString()
                    loadedImage(url)
                }
                .setNegativeButton("Cancelar") { _, _ -> }
                .show()
        }
    }
}