package fr.epf.min2.ecommerceapp.model

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)
