package fr.epf.min2.ecommerceapp

import android.util.Log
import fr.epf.min2.ecommerceapp.model.CartItem
import fr.epf.min2.ecommerceapp.model.Product

object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    fun addToCart(product: Product) {
        Log.d("CartManager", "addToCart called with product id=${product.id}")
        val existingItem = cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(CartItem(product))
        }
    }

    fun removeFromCart(product: Product) {
        val existingItem = cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            cartItems.remove(existingItem)
        }
    }

    fun changeQuantity(product: Product, quantity: Int) {
        val item = cartItems.find { it.product.id == product.id }
        if (item != null) {
            item.quantity = quantity
            if (item.quantity <= 0) {
                cartItems.remove(item)
            }
        }
    }

    fun getCart(): List<CartItem> = cartItems.toList()

    fun getTotalPrice(): Double = cartItems.sumOf { it.product.price * it.quantity }

    fun clearCart() {
        cartItems.clear()
    }
}
