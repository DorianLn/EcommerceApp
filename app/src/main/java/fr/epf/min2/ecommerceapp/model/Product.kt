package fr.epf.min2.ecommerceapp.model

import java.io.Serializable

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
) : Serializable