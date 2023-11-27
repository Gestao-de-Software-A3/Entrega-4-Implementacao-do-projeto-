package com.example.basket.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.example.basket.R
import com.example.basket.core.PRODUCT_KEY_ID
import com.example.basket.databinding.ProductListActivityBinding
import com.example.basket.domain.usecases.ProductUseCases
import com.example.basket.presentation.adapters.ProductListAdapter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.math.log

class ProductListActivity : BaseUserActivity() {
    
    private val productUseCases: ProductUseCases by inject()
    
    private val adapter = ProductListAdapter(context = this)
    private val binding by lazy {
        ProductListActivityBinding.inflate(layoutInflater)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRecyclerView()
        setFab()
        lifecycleScope.launch {
            launch {
                user
                    .filterNotNull()
                    .collect() { user ->
                        searchUserProducts(user.id)
                    }
            }
        }
    }
    
    private suspend fun searchUserProducts(userId: String) {
        productUseCases.findUserProductsUseCase(userId).collect() { products ->
            adapter.setData(products)
        }
    }
    
    private fun setRecyclerView() {
        val recyclerView = binding.productListRecyclerView
        recyclerView.adapter = adapter
        toProductDetails()
    }
    
    private fun toProductDetails() {
        adapter.clickOnProduct = {
            val intent = Intent(this, ProductDetailsActivity::class.java).apply {
                putExtra(PRODUCT_KEY_ID, it.id)
            }
            startActivity(intent)
        }
    }
    
    private fun setFab() {
        val fab = binding.productListFab
        fab.setOnClickListener {
            goToProductForm()
        }
    }
    
    private fun goToProductForm() {
        val intent = Intent(this, ProductFormActivity::class.java)
        startActivity(intent)
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                lifecycleScope.launch {
                    logOut()
                }
            }
        }
        return super.onOptionsItemSelected(item)
        
    }
}