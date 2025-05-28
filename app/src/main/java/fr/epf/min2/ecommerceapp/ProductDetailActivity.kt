package fr.epf.min2.ecommerceapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import android.widget.TextView
import android.widget.ImageView
import android.widget.Button
import android.widget.Toast
import fr.epf.min2.ecommerceapp.model.Product
import fr.epf.min2.ecommerceapp.CartManager
import androidx.lifecycle.lifecycleScope
import fr.epf.min2.ecommerceapp.network.RetrofitInstance
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val productId = intent.getStringExtra("product_id")

        if (productId != null) {
            // Cas QR code : charger depuis l’API
            val id = productId.toIntOrNull()
            if (id != null) {
                fetchProductById(id)
            } else {
                Toast.makeText(this, "ID produit invalide", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            // Cas classique : infos passées via Intent
            val productName = intent.getStringExtra("product_name")
            val productPrice = intent.getDoubleExtra("product_price", 0.0)
            val productDescription = intent.getStringExtra("product_description")
            val productImage = intent.getStringExtra("product_image")

            val product = Product(
                id = 0,
                title = productName ?: "",
                price = productPrice,
                description = productDescription ?: "",
                image = productImage ?: "",
                category = ""
            )

            displayProduct(product)
        }
    }

    private fun fetchProductById(id: Int) {
        lifecycleScope.launch {
            try {
                val product = RetrofitInstance.api.getProductById(id)
                displayProduct(product)
            } catch (e: Exception) {
                Toast.makeText(this@ProductDetailActivity, "Erreur lors du chargement du produit", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun displayProduct(product: Product) {
        findViewById<TextView>(R.id.productName).text = product.title
        findViewById<TextView>(R.id.productPrice).text = "${product.price} €"
        findViewById<TextView>(R.id.productDescription).text = product.description

        Glide.with(this)
            .load(product.image)
            .into(findViewById(R.id.productImage))

        val addToCartButton = findViewById<Button>(R.id.addToCartButton)
        addToCartButton.setOnClickListener {
            CartManager.addToCart(product)
            Toast.makeText(this, "Produit ajouté au panier", Toast.LENGTH_SHORT).show()
        }
    }

}
