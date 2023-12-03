package com.example.entregapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.entregapp.R
import com.example.entregapp.models.CompraModel

class CompraAdapter(private var lstCompras: List<CompraModel>)
    : RecyclerView.Adapter<CompraAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(producto: CompraModel)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCompraProduct: TextView = itemView.findViewById(R.id.tvCompraProduct)
        val tvCompraClient: TextView = itemView.findViewById(R.id.tvCompraCliente)
        val tvCompraPrice: TextView = itemView.findViewById(R.id.tvCompraPrice)
        val tvCompraFecha: TextView = itemView.findViewById(R.id.tvCompraFecha)
        val tvCompraEstado: TextView = itemView.findViewById(R.id.tvCompraEstado)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(lstCompras[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_compra, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = lstCompras[position]
        holder.tvCompraProduct.text = producto.producto?.name ?: ""
        holder.tvCompraClient.text = producto.user_name
        holder.tvCompraFecha.text = producto.fecha
        holder.tvCompraPrice.text = producto.producto?.price.toString()
        holder.tvCompraEstado.text = producto.status
    }

    override fun getItemCount(): Int {
        return lstCompras.size
    }
}
