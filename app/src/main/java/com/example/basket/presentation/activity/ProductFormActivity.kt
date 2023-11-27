package com.example.basket.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.R
import androidx.lifecycle.lifecycleScope
import com.example.basket.core.PRODUCT_KEY_ID
import com.example.basket.core.loadImage
import com.example.basket.databinding.ProductFormActivityBinding
import com.example.basket.domain.model.Product
import com.example.basket.domain.model.User
import com.example.basket.domain.usecases.ProductUseCases
import com.example.basket.domain.usecases.UserUseCases
import com.example.basket.presentation.dialog.ImageFormDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.math.BigDecimal

class ProductFormActivity : BaseUserActivity() {
    
    //(private val productUseCases: ProductUseCases)
    private val productUseCases: ProductUseCases by inject()
    
    private val binding by lazy {
        ProductFormActivityBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var productId = 0L
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSaveButton()
        setContentView(binding.root)
        title = "Cadastrar Pizza"
        binding.productFormImage.setOnClickListener() {
            ImageFormDialog(this)
                .showDialog(url) { image ->
                    url = image
                    binding.productFormImage.loadImage(url)
                }
        }
        loadProduct()
    }
    
    override fun onResume() {
        super.onResume()
        searchProduct()
    }
    
    private fun searchProduct() {
        lifecycleScope.launch {
            productUseCases.findProductByIdUseCase(productId).collect { product ->
                product?.let {
                    title = "Alterar Produto"
                    binding.productFormUser.visibility =
                        if (product.savedWithNoUser()) {
                            setUserField()
                            View.VISIBLE
                        } else View.GONE
                    fillFields(it)
                }
            }
        }
    }
    
    private fun setUserField() {
        lifecycleScope.launch {
            allUsers().invoke().map { users ->
                users.map { it.id }
            }.collect() { users ->
                setAutoCompleteTextView(users)
            }
        }
    }
    
    private fun setAutoCompleteTextView(users: List<String>) {
        val userField = binding.productFormUser
        val adapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            users
        )
        userField.setAdapter(adapter)
        userField.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                existingUser(users)
            }
        }
    }
    
    private fun existingUser(users: List<String>): Boolean {
        val userField = binding.productFormUser
        val userId = userField.text.toString()
        if (!users.contains(userId)) {
            userField.error = "usuário inexistente!"
            return false
        }
        return true
    }
    
    private fun loadProduct() {
        productId = intent.getLongExtra(PRODUCT_KEY_ID, 0L)
    }
    
    private fun fillFields(product: Product) {
        url = product.image
        with(binding) {
            productFormImage.loadImage(product.image)
            productFormName.setText(product.name)
            productFormDetails.setText(product.description)
            productFormPrice.setText(product.price.toPlainString())
        }
    }
    
    private fun setSaveButton() {
        val saveButton = binding.productFormSaveButton
        
        saveButton.setOnClickListener {
            lifecycleScope.launch {
                saveProduct()
            }
        }
    }
    
    private suspend fun saveProduct() {
        user.value?.let { user ->
            try {
                val userId = defineUser(user)
                val newProduct = createProduct(userId)
                productUseCases.addProductUseCase(newProduct)
                finish()
            } catch (e: RuntimeException) {
                Log.e("FormularioProduto", "configuraBotaoSalvar:", e)
            }
        }
    }
    
    private suspend fun defineUser(user: User): String = productUseCases
        .findProductByIdUseCase(productId)
        .first()?.let { product ->
            if (product.userId.isNullOrBlank()) {
                val users = allUsers().invoke()
                    .map { users ->
                        users.map { it.id }
                    }.first()
                if (existingUser(users)) {
                    val userField = binding.productFormUser
                    return userField.text.toString()
                } else {
                    throw RuntimeException("Tentou salvar produto com usuário Inexistente")
                }
            }
            null
        } ?: user.id
    
    private fun createProduct(userId: String): Product {
        val nameField = binding.productFormName
        val name = nameField.text.toString()
        val descriptionField = binding.productFormDetails
        val description = descriptionField.text.toString()
        val priceField = binding.productFormPrice
        val priceText = priceField.text.toString()
        
        val price = if (priceText.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(priceText)
        }
        
        return Product(
            id = productId,
            name = name,
            description = description,
            price = price,
            image = url,
            userId = userId
        )
    }
    
}