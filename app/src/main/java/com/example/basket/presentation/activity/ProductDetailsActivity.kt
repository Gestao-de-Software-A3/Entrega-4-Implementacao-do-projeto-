package com.example.basket.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.basket.R
import com.example.basket.core.PRODUCT_KEY_ID
import com.example.basket.core.formatToBrazilianCurrency
import com.example.basket.core.loadImage
import com.example.basket.databinding.ProductDetailsActivityBinding
import com.example.basket.domain.model.Product
import com.example.basket.domain.usecases.ProductUseCases
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * Atividade responsável pela exibição de detalhes de um produto, incluindo opções para editar ou remover o produto.
 *
 * @property productUseCases Instâncias dos casos de uso relacionados a produtos.
 * @property productId ID do produto a ser exibido.
 * @property product Produto atualmente carregado.
 * @property binding Objeto de ligação para a interface do usuário usando View Binding.
 */
class ProductDetailsActivity : AppCompatActivity() {
    
    // Instâncias dos casos de uso relacionados a produtos
    private val productUseCases: ProductUseCases by inject()
    
    // ID do produto a ser exibido
    private var productId: Long = 0L
    
    // Produto atualmente carregado
    private var product: Product? = null
    
    // Objeto de ligação para a interface do usuário usando View Binding
    private val binding by lazy {
        ProductDetailsActivityBinding.inflate(layoutInflater)
    }
    
    /**
     * Chamado quando a atividade está sendo criada.
     * Carrega o ID do produto da intenção e exibe os detalhes do produto.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadProduct()
    }
    
    /**
     * Chamado quando a atividade está retomando.
     * Atualiza os detalhes do produto ao retomar a atividade.
     */
    override fun onResume() {
        super.onResume()
        searchProduct()
    }
    
    /**
     * Cria o menu de opções na barra de ação.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_details_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    
    /**
     * Manipula os itens do menu de opções.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.details_menu_remove -> {
                lifecycleScope.launch {
                    product?.let {
                        productUseCases.deleteProductUseCase(it)
                        finish()
                    }
                }
            }
            
            R.id.details_menu_edit -> {
                Intent(this, ProductFormActivity::class.java).apply {
                    putExtra(PRODUCT_KEY_ID, productId)
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    
    /**
     * Carrega o ID do produto da intenção.
     */
    private fun loadProduct() {
        productId = intent.getLongExtra(PRODUCT_KEY_ID, 0)
    }
    
    /**
     * Preenche os campos da interface do usuário com os detalhes do produto carregado.
     *
     * @param loadedProduct Produto carregado para exibição.
     */
    private fun fillFields(loadedProduct: Product) {
        with(binding) {
            detailsProductImage.loadImage(loadedProduct.image)
            detailsProductName.text = loadedProduct.name
            detailsProductDescription.text = loadedProduct.description
            detailsProductPrice.text = loadedProduct.price.formatToBrazilianCurrency()
        }
    }
    
    /**
     * Busca o produto pelo ID e atualiza os detalhes da interface do usuário.
     */
    private fun searchProduct() {
        lifecycleScope.launch {
            productUseCases.findProductByIdUseCase(productId).collect { productFound ->
                product = productFound
                product?.let {
                    fillFields(it)
                } ?: finish()
            }
        }
    }
}