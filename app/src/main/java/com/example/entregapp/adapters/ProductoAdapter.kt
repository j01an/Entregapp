package com.example.entregapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.entregapp.R
import com.example.entregapp.models.ProductoModel

class ProductoAdapter(private var lstProducts: List<ProductoModel>)
    : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(producto: ProductoModel)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvProductoName: TextView = itemView.findViewById(R.id.tvCompraPrice)
        val tvProductoPRecio: TextView = itemView.findViewById(R.id.tvCompraProduct)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(lstProducts[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = lstProducts[position]
        holder.tvProductoName.text = producto.name
        holder.tvProductoPRecio.text = producto.price.toString()
    }

    override fun getItemCount(): Int {
        return lstProducts.size
    }
}
