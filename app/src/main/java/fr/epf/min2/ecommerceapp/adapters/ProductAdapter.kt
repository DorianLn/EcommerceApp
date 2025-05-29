package fr.epf.min2.ecommerceapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.epf.min2.ecommerceapp.ProductDetailActivity
import fr.epf.min2.ecommerceapp.R
import fr.epf.min2.ecommerceapp.model.Product

class ProductAdapter(
    private val context: Context,
    private val productList: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.productName)
        val priceTextView: TextView = itemView.findViewById(R.id.productPrice)
        val imageView: ImageView = itemView.findViewById(R.id.productImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.nameTextView.text = product.title
        holder.priceTextView.text = "${product.price} €"
        Glide.with(context).load(product.image).into(holder.imageView)

        holder.itemView.setOnClickListener {
            Log.d("ProductAdapter", "Clicked product: id=${product.id}, name=${product.title}")

            val intent = Intent(holder.itemView.context, ProductDetailActivity::class.java)
            intent.putExtra("product_id", product.id)
            intent.putExtra("product_name", product.title)
            intent.putExtra("product_price", product.price)
            intent.putExtra("product_description", product.description)
            intent.putExtra("product_image", product.image)
            holder.itemView.context.startActivity(intent)
        }
    }
}