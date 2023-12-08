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

/**
 * Atividade responsável pelo formulário de produtos, permitindo a adição ou edição de produtos.
 *
 * @property productUseCases Instâncias dos casos de uso relacionados a produtos.
 * @property binding Objeto de ligação para a interface do usuário usando View Binding.
 * @property url URL da imagem do produto.
 * @property productId ID do produto em edição, se aplicável.
 */
class ProductFormActivity : BaseUserActivity() {
    
    // Instâncias dos casos de uso relacionados a produtos
    private val productUseCases: ProductUseCases by inject()
    
    // Objeto de ligação para a interface do usuário usando View Binding
    private val binding by lazy {
        ProductFormActivityBinding.inflate(layoutInflater)
    }
    
    // URL da imagem do produto
    private var url: String? = null
    
    // ID do produto em edição, se aplicável
    private var productId = 0L
    
    /**
     * Chamado quando a atividade está sendo criada.
     * Configura a interface do usuário, incluindo os botões de salvar e ação para a imagem do produto.
     * Carrega as informações do produto, se aplicável.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSaveButton()
        setContentView(binding.root)
        title = "Cadastrar Produto"
        binding.productFormImage.setOnClickListener() {
            ImageFormDialog(this)
                .showDialog(url) { image ->
                    url = image
                    binding.productFormImage.loadImage(url)
                }
        }
        loadProduct()
    }
    
    /**
     * Chamado quando a atividade está retomando.
     * Atualiza as informações do produto ao retomar a atividade.
     */
    override fun onResume() {
        super.onResume()
        searchProduct()
    }
    
    /**
     * Busca as informações do produto pelo ID e preenche o formulário com os detalhes do produto.
     */
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
    
    /**
     * Define o campo de usuário no formulário, preenchendo-o com uma lista de usuários disponíveis.
     */
    private fun setUserField() {
        lifecycleScope.launch {
            allUsers().invoke().map { users ->
                users.map { it.id }
            }.collect() { users ->
                setAutoCompleteTextView(users)
            }
        }
    }
    
    /**
     * Configura um adaptador e um ouvinte para o campo de usuário no formulário.
     */
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
    
    /**
     * Valida se o usuário existente no campo de usuário faz parte da lista de usuários disponíveis.
     */
    private fun existingUser(users: List<String>): Boolean {
        val userField = binding.productFormUser
        val userId = userField.text.toString()
        if (!users.contains(userId)) {
            userField.error = "usuário inexistente!"
            return false
        }
        return true
    }
    
    /**
     * Carrega o ID do produto da intenção.
     */
    private fun loadProduct() {
        productId = intent.getLongExtra(PRODUCT_KEY_ID, 0L)
    }
    
    /**
     * Preenche os campos do formulário com os detalhes do produto.
     *
     * @param product Produto a ser exibido no formulário.
     */
    private fun fillFields(product: Product) {
        url = product.image
        with(binding) {
            productFormImage.loadImage(product.image)
            productFormName.setText(product.name)
            productFormDetails.setText(product.description)
            productFormPrice.setText(product.price.toPlainString())
        }
    }
    
    /**
     * Configura o botão de salvar no formulário.
     */
    private fun setSaveButton() {
        val saveButton = binding.productFormSaveButton
        
        saveButton.setOnClickListener {
            lifecycleScope.launch {
                saveProduct()
            }
        }
    }
    
    /**
     * Salva as informações do produto no banco de dados com base nos campos do formulário.
     */
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
    
    /**
     * Define o usuário associado ao produto com base nas regras de negócios.
     *
     * @param user Usuário atualmente logado.
     * @return ID do usuário associado ao produto.
     */
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
    
    /**
     * Cria uma instância de produto com base nos campos do formulário.
     *
     * @param userId ID do usuário associado ao produto.
     * @return Nova instância de produto.
     */
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