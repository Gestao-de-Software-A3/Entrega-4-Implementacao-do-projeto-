package com.example.basket.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basket.core.formatToBrazilianCurrency
import com.example.basket.core.loadImage
import com.example.basket.databinding.ProductItemBinding
import com.example.basket.domain.model.Product

/**
 * Adaptador para exibir uma lista de produtos em um RecyclerView.
 *
 * @property context Contexto associado ao adaptador.
 * @property products Lista inicial de produtos a serem exibidos.
 * @property clickOnProduct Ação a ser executada quando um produto é clicado.
 */
class ProductListAdapter(
    val context: Context,
    products: List<Product> = emptyList(),
    var clickOnProduct: (product: Product) -> Unit = {},
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {
    
    /**
     * ViewHolder para representar a exibição de um item de produto no RecyclerView.
     *
     * @property binding Objeto de ligação para a interface do usuário usando View Binding.
     */
    inner class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        // Produto associado a esta ViewHolder
        private lateinit var product: Product
        
        /**
         * Associa os dados do produto à interface do usuário.
         *
         * @param product Produto a ser exibido.
         */
        fun bindView(product: Product) {
            this.product = product
            with(binding) {
                productName.text = product.name
                productDescription.text = product.description
                productPrice.text = product.price.formatToBrazilianCurrency()
                imageView.loadImage(product.image)
            }
        }
        
        // Configura a ação de clique no item do RecyclerView
        init {
            itemView.setOnClickListener {
                if (::product.isInitialized) {
                    clickOnProduct(product)
                }
            }
        }
    }
    
    // Lista mutável de produtos no adaptador
    private val productList = products.toMutableList()
    
    /**
     * Cria e retorna uma nova instância de ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductItemBinding.inflate(inflater, parent, false)
        
        return ProductViewHolder(binding)
    }
    
    /**
     * Retorna o número total de itens no conjunto de dados.
     */
    override fun getItemCount(): Int = productList.size
    
    /**
     * Vincula os dados do produto a uma ViewHolder específica.
     */
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindView(productList[position])
    }
    
    /**
     * Atualiza os dados da lista de produtos e notifica o adaptador sobre as mudanças.
     *
     * @param newProductList Nova lista de produtos a ser exibida.
     */
    fun setData(newProductList: List<Product>) {
        notifyItemRangeRemoved(0, productList.size)
        productList.clear()
        productList.addAll(newProductList)
        notifyItemInserted(newProductList.size)
    }
}