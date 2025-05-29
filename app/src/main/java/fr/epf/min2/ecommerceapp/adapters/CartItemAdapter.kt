package fr.epf.min2.ecommerceapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import fr.epf.min2.ecommerceapp.model.CartItem

class CartItemAdapter(
    private val context: Context,
    private val items: MutableList<CartItem>,
    private val updateUI: () -> Unit
) : BaseAdapter() {

    override fun getCount() = items.size
    override fun getItem(position: Int) = items[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)

        val item = items[position]

        view.findViewById<TextView>(R.id.cart_item_title).text = item.product.title
        view.findViewById<TextView>(R.id.cart_item_price).text =
            "${String.format("%.2f", item.product.price)} € l’unité"
        view.findViewById<TextView>(R.id.cart_item_quantity).text = item.quantity.toString()

        view.findViewById<Button>(R.id.btn_increase).setOnClickListener {
            CartManager.changeQuantity(item.product, item.quantity + 1)
            refresh()
        }

        view.findViewById<Button>(R.id.btn_decrease).setOnClickListener {
            CartManager.changeQuantity(item.product, item.quantity - 1)
            refresh()
        }

        view.findViewById<Button>(R.id.btn_remove).setOnClickListener {
            CartManager.removeFromCart(item.product)
            refresh()
        }

        return view
    }

    private fun refresh() {
        items.clear()
        items.addAll(CartManager.getCart())
        notifyDataSetChanged()
        updateUI()
    }
}
