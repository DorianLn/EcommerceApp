package fr.epf.min2.ecommerceapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import fr.epf.min2.ecommerceapp.model.Product

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val toolbar = findViewById<Toolbar>(R.id.cart_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val cartListView = findViewById<ListView>(R.id.cartListView)
        val cartItems: List<Product> = CartManager.getCart()
        val itemTitles = cartItems.map { "${it.title} - ${it.price} €" }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemTitles)
        cartListView.adapter = adapter
    }
}
