package com.example.basket.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basket.core.formatToBrazilianCurrency
import com.example.basket.core.loadImage
import com.example.basket.databinding.ProductItemBinding
import com.example.basket.domain.model.Product

class ProductListAdapter(
    val context: Context,
    products: List<Product> = emptyList(),
    var clickOnProduct: (product: Product) -> Unit = {},
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {
    
    inner class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    
        private lateinit var product: Product
        
        fun bindView(product: Product) {
            this.product = product
            with(binding) {
                productName.text = product.name
                productDescription.text = product.description
                productPrice.text = product.price.formatToBrazilianCurrency()
                imageView.loadImage(product.image)
            }
        }
        
        init {
            itemView.setOnClickListener {
                if (::product.isInitialized) {
                    clickOnProduct(product)
                }
            }
        }
    }
    
    private val productList = products.toMutableList()
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductItemBinding.inflate(inflater, parent, false)
        
        return ProductViewHolder(binding)
    }
    
    override fun getItemCount(): Int = productList.size
    
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindView(productList[position])
    }
    
    fun setData(newProductList: List<Product>) {
        notifyItemRangeRemoved(0, productList.size)
        productList.clear()
        productList.addAll(newProductList)
        notifyItemInserted(newProductList.size)
    }
}