package com.example.basket.presentation.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
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

/**
 * Atividade responsável por exibir a lista de produtos do usuário logado,
 * permitindo a navegação para detalhes de produtos ou adição de novos produtos.
 *
 * @property productUseCases Instâncias dos casos de uso relacionados a produtos.
 * @property adapter Adaptador para exibir a lista de produtos no RecyclerView.
 * @property binding Objeto de ligação para a interface do usuário usando View Binding.
 */
class ProductListActivity : BaseUserActivity() {
    
    // Instâncias dos casos de uso relacionados a produtos
    private val productUseCases: ProductUseCases by inject()
    
    // Adaptador para exibir a lista de produtos no RecyclerView
    private val adapter = ProductListAdapter(context = this)
    
    // Objeto de ligação para a interface do usuário usando View Binding
    private val binding by lazy {
        ProductListActivityBinding.inflate(layoutInflater)
    }
    
    /**
     * Chamado quando a atividade está sendo criada.
     * Configura a interface do usuário, incluindo o RecyclerView, FAB (Floating Action Button)
     * para adicionar novos produtos e a lógica para lidar com a navegação para os detalhes do produto.
     */
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
    
    /**
     * Realiza a busca e exibição dos produtos associados ao usuário logado.
     *
     * @param userId ID do usuário logado.
     */
    private suspend fun searchUserProducts(userId: String) {
        productUseCases.findUserProductsUseCase(userId).collect() { products ->
            adapter.setData(products)
        }
    }
    
    /**
     * Configura o RecyclerView para exibir a lista de produtos.
     * Define a ação de clique em um produto para navegar para os detalhes do produto.
     */
    private fun setRecyclerView() {
        val recyclerView = binding.productListRecyclerView
        recyclerView.adapter = adapter
        toProductDetails()
    }
    
    /**
     * Define a ação de clique em um produto para navegar para os detalhes do produto.
     */
    private fun toProductDetails() {
        adapter.clickOnProduct = {
            val intent = Intent(this, ProductDetailsActivity::class.java).apply {
                putExtra(PRODUCT_KEY_ID, it.id)
            }
            startActivity(intent)
        }
    }
    
    /**
     * Configura o FAB (Floating Action Button) para adicionar novos produtos.
     */
    private fun setFab() {
        val fab = binding.productListFab
        fab.setOnClickListener {
            goToProductForm()
        }
        
        fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimary)))
    }
    
    /**
     * Navega para a tela de formulário de produto ao clicar no FAB.
     */
    private fun goToProductForm() {
        val intent = Intent(this, ProductFormActivity::class.java)
        startActivity(intent)
    }
    
    /**
     * Cria o menu de opções na barra de ação.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    
    /**
     * Manipula a seleção de itens no menu de opções da barra de ação.
     */
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