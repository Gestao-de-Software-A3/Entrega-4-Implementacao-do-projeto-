package com.example.basket

import com.example.basket.domain.model.Product
import com.example.basket.domain.repository.ProductRepository
import com.example.basket.domain.usecases.productUseCases.AddProductUseCase
import com.example.basket.domain.usecases.productUseCases.DeleteProductUseCase
import com.example.basket.domain.usecases.productUseCases.FindProductByIdUseCase
import com.example.basket.domain.usecases.productUseCases.FindUserProductsUseCase
import com.example.basket.domain.usecases.productUseCases.GetAllProductsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductUseCasesTests {
    
    private lateinit var addProductUseCase: AddProductUseCase
    private lateinit var deleteProductUseCase: DeleteProductUseCase
    private lateinit var productRepository: ProductRepository
    private lateinit var getAllProductsUseCase: GetAllProductsUseCase
    private lateinit var findProductByIdUseCase: FindProductByIdUseCase
    private lateinit var findUserProductsUseCase: FindUserProductsUseCase
    
    
    @Before
    fun setUp() {
        productRepository = mockk()
        addProductUseCase = AddProductUseCase(productRepository)
        deleteProductUseCase = DeleteProductUseCase(productRepository)
        getAllProductsUseCase = GetAllProductsUseCase(productRepository)
        findProductByIdUseCase = FindProductByIdUseCase(productRepository)
        findUserProductsUseCase = FindUserProductsUseCase(productRepository)
    }
    
    @Test
    fun invokeShouldCallAddProductInRepository() = runBlocking {
        val product = mockk<Product>()
        
        coEvery { productRepository.addProduct(product) } returns Unit
        
        addProductUseCase(product)
        
        coVerify(exactly = 1) { productRepository.addProduct(product) }
    }
    
    @Test
    fun invokeShouldCallDeleteProductInRepository() = runBlocking {
        val product = mockk<Product>()
        
        coEvery { productRepository.deleteProduct(product) } returns Unit
        
        deleteProductUseCase(product)
        
        coVerify(exactly = 1) { productRepository.deleteProduct(product) }
    }
    
    @Test
    fun invokeShouldReturnFlowOfAllProductsFromRepository() = runBlocking {
        val productsFlow = flowOf<List<Product>>(emptyList())
        
        every { productRepository.getAllProducts() } returns productsFlow
        
        val result = getAllProductsUseCase.invoke()
        
        assertEquals(productsFlow, result)
        verify(exactly = 1) { productRepository.getAllProducts() }
    }
    
    @Test
    fun invokeShouldReturnFlowOfProductByIdFromRepository() = runBlocking {
        val productId = 123L
        val productFlow = flowOf<Product?>(mockk())
        
        every { productRepository.findProductById(productId) } returns productFlow
        
        val result = findProductByIdUseCase.invoke(productId)
        
        assertEquals(productFlow, result)
        verify(exactly = 1) { productRepository.findProductById(productId) }
    }
    
    @Test
    fun invokeShouldReturnFlowOfUserProductsFromRepository() = runBlocking {
        val userId = "user123"
        val productsFlow = flowOf<List<Product>>(emptyList())
        
        every { productRepository.findUserProducts(userId) } returns productsFlow
        
        val result = findUserProductsUseCase.invoke(userId)
        
        assertEquals(productsFlow, result)
        verify(exactly = 1) { productRepository.findUserProducts(userId) }
    }
    
}