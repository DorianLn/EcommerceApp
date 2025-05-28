package fr.epf.min2.ecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.epf.min2.ecommerceapp.model.Product
import fr.epf.min2.ecommerceapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                _products.value = RetrofitInstance.api.getAllProducts()
            } catch (e: Exception) {
                // Log the error or handle appropriately
            }
        }
    }
}