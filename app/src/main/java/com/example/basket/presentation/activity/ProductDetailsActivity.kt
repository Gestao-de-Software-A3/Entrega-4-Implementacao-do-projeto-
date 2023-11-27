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

class ProductDetailsActivity : AppCompatActivity() {
    
    //(private val productUseCases: ProductUseCases)
    private val productUseCases: ProductUseCases by inject()
    
    private var productId: Long = 0L
    private var product: Product? = null
    private val binding by lazy {
        ProductDetailsActivityBinding.inflate(layoutInflater)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadProduct()
    }
    
    override fun onResume() {
        super.onResume()
        searchProduct()
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_details_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    
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
    
    private fun loadProduct() {
        productId = intent.getLongExtra(PRODUCT_KEY_ID, 0)
    }
    
    private fun fillFields(loadedProduct: Product) {
        with(binding) {
            detailsProductImage.loadImage(loadedProduct.image)
            detailsProductName.text = loadedProduct.name
            detailsProductDescription.text = loadedProduct.description
            detailsProductPrice.text = loadedProduct.price.formatToBrazilianCurrency()
        }
    }
    
    private fun searchProduct() {
        lifecycleScope.launch {
            productUseCases.findProductByIdUseCase(productId).collect() { productFound ->
                product = productFound
                product?.let {
                    fillFields(it)
                } ?: finish()
            }
        }
    }
}