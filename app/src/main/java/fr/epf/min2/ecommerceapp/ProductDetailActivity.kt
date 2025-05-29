package fr.epf.min2.ecommerceapp

import android.os.Bundle
import android.util.Log
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

        val qrProductId = intent.getStringExtra("qr_product_id")
        if (qrProductId != null) {
            val id = qrProductId.toIntOrNull()
            if (id != null) {
                fetchProductById(id)
                return
            }
        }

// Cas classique : ouvert depuis ProductAdapter
        val productId = intent.getIntExtra("product_id", -1)
        val productName = intent.getStringExtra("product_name")
        val productPrice = intent.getDoubleExtra("product_price", 0.0)
        val productDescription = intent.getStringExtra("product_description")
        val productImage = intent.getStringExtra("product_image")

        val product = Product(
            id = productId,
            title = productName ?: "",
            price = productPrice,
            description = productDescription ?: "",
            image = productImage ?: "",
            category = ""
        )

        displayProduct(product)


    }

    private fun fetchProductById(id: Int) {
        lifecycleScope.launch {
            try {
                val product = RetrofitInstance.api.getProductById(id)
                displayProduct(product)
            } catch (e: Exception) {
                Toast.makeText(
                    this@ProductDetailActivity,
                    "Erreur lors du chargement du produit",
                    Toast.LENGTH_SHORT
                ).show()
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
