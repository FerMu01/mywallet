package com.example.mywallet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BitcoinAdapter(private val data: List<BitcoinData>) :
    RecyclerView.Adapter<BitcoinAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fechaTextView: TextView = view.findViewById(R.id.textFecha)
        val valorTextView: TextView = view.findViewById(R.id.textValor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bitcoin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.fechaTextView.text = item.fecha.substring(0, 10) // Formato YYYY-MM-DD
        holder.valorTextView.text = "$${item.valor}"
    }

    override fun getItemCount() = data.size
}
