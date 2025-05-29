package fr.epf.min2.ecommerceapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import fr.epf.min2.ecommerceapp.model.CartItem

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val totalTextView = findViewById<TextView>(R.id.cartTotalPrice)

        val toolbar = findViewById<Toolbar>(R.id.cart_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val cartListView = findViewById<ListView>(R.id.cartListView)
        val cartItems: List<CartItem> = CartManager.getCart()


        val cartItemsList = CartManager.getCart().toMutableList()
        val adapter = CartItemAdapter(this, cartItemsList) {
            totalTextView.text = "Total = ${String.format("%.2f", CartManager.getTotalPrice())} €"
        }

        cartListView.adapter = adapter


        // Affichage du prix total
        val totalPrice = CartManager.getTotalPrice()
        Toast.makeText(this, "Total : ${String.format("%.2f", totalPrice)} €", Toast.LENGTH_LONG).show()

        // Click sur un produit pour modifier sa quantité ou le supprimer
        cartListView.setOnItemClickListener { _, _, position, _ ->
            val item = cartItems[position]
            val options = arrayOf("➕ Augmenter", "➖ Diminuer", "🗑 Supprimer")

            AlertDialog.Builder(this)
                .setTitle(item.product.title)
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> CartManager.changeQuantity(item.product, item.quantity + 1)
                        1 -> CartManager.changeQuantity(item.product, item.quantity - 1)
                        2 -> CartManager.removeFromCart(item.product)
                    }
                    adapter.notifyDataSetChanged()

                    totalTextView.text = "Total = ${String.format("%.2f", CartManager.getTotalPrice())} €"
                }
                .show()
        }


        totalTextView.text = "Total = ${String.format("%.2f", CartManager.getTotalPrice())} €"

        val payButton = findViewById<Button>(R.id.payButton)
        payButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Confirmation de paiement")
                .setMessage("Confirmer le paiement de ${String.format("%.2f", CartManager.getTotalPrice())} € ?")
                .setPositiveButton("Confirmer") { _, _ ->
                    // 1. Vider le panier
                    CartManager.clearCart()

                    // 2. Mettre à jour l'adapter
                    cartItemsList.clear()
                    cartItemsList.addAll(CartManager.getCart())
                    adapter.notifyDataSetChanged()

                    // 3. Mettre à jour le total
                    totalTextView.text = "Total = 0.00 €"

                    // 4. Message de succès
                    Toast.makeText(this, "Paiement effectué avec succès", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Annuler", null)
                .show()
        }

    }
    private fun showPaymentConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Confirmer le paiement")
            .setMessage("Total à payer : ${String.format("%.2f", CartManager.getTotalPrice())} €.\nVoulez-vous continuer ?")
            .setPositiveButton("Oui") { _, _ ->
                simulatePayment()
            }
            .setNegativeButton("Annuler", null)
            .show()
    }
    private fun simulatePayment() {
        // Simulation : on affiche un message, on vide le panier
        Toast.makeText(this, "Paiement effectué avec succès 🎉", Toast.LENGTH_LONG).show()

        CartManager.clearCart()

        // Rafraîchissement UI
        val cartListView = findViewById<ListView>(R.id.cartListView)
        val totalTextView = findViewById<TextView>(R.id.cartTotalPrice)

        val updatedAdapter = CartItemAdapter(
            this,
            CartManager.getCart().toMutableList()
        ) {
            totalTextView.text = "Total = ${String.format("%.2f", CartManager.getTotalPrice())} €"
            }

        }

    }
