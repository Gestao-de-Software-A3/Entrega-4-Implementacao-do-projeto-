package com.example.basket

import com.example.basket.data.database.dao.ProductDao
import com.example.basket.data.repository.ProductRepositoryImpl
import com.example.basket.domain.model.Product
import com.example.basket.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class ProductRepositoryImplTests {
    
    private lateinit var repository: ProductRepository
    private lateinit var productDao: ProductDao
    
    @Before
    fun setUp() {
        productDao = mockk()
        repository = ProductRepositoryImpl(productDao)
    }
    
    @Test
    fun testAddProductShouldCallProductDao() = runBlocking {
        val product = mockk<Product>()
        
        coEvery { productDao.add(product) } returns Unit
        
        repository.addProduct(product)
        
        coVerify(exactly = 1) { productDao.add(product) }
    }
    
    @Test
    fun testDeleteProductShouldCallProductDao() = runBlocking {
        val product = mockk<Product>()
        
        coEvery { productDao.delete(product) } returns Unit
        
        repository.deleteProduct(product)
        
        coVerify(exactly = 1) { productDao.delete(product) }
    }
    
    @Test
    fun testGetAllProductsShouldReturnFlow() = runBlocking {
        val products = listOf(mockk<Product>())
        
        every { productDao.searchAll() } returns flowOf(products)
        
        val result = repository.getAllProducts()
        
        result.collect {
            Assert.assertEquals(products, it)
        }
        
        verify(exactly = 1) { productDao.searchAll() }
    }
    
    @Test
    fun testFindProductByIdShouldReturnFlow() = runBlocking {
        val productId = 50L
        val product = mockk<Product>()
        
        every { productDao.findProductById(productId) } returns flowOf(product)
        
        val result = repository.findProductById(productId)
        
        result.collect {
            Assert.assertEquals(product, it)
        }
        
        verify(exactly = 1) { productDao.findProductById(productId) }
    }
    
    @Test
    fun testFindUserProductsShouldReturnFlow() = runBlocking {
        val userId = "user50"
        val products = mockk<List<Product>>()
        
        every { productDao.findUserProducts(userId) } returns flowOf(products)
        
        val result = repository.findUserProducts(userId)
        
        result.collect {
            Assert.assertEquals(products, it)
        }
        
        verify(exactly = 1) {
            productDao.findUserProducts(userId)
        }
    }
}