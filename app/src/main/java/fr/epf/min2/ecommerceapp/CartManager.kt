package fr.epf.min2.ecommerceapp

import fr.epf.min2.ecommerceapp.model.Product

object CartManager {
    private val cart = mutableListOf<Product>()

    fun addToCart(product: Product) {
        cart.add(product)
    }

    fun getCart(): List<Product> {
        return cart.toList()
    }

    fun clearCart() {
        cart.clear()
    }
}
