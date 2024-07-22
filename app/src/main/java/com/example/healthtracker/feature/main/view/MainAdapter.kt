package com.example.healthtracker.feature.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthtracker.R

class MainAdapter(
    private var items: List<MainItem>,
    private val callback: (id: Int) -> Unit
): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false)
        return MainViewHolder(view)
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MainItem) {
            val itemText: TextView = itemView.findViewById(R.id.item_text)
            val itemIcon: ImageView = itemView.findViewById(R.id.item_icon)
            itemText.setText(item.text)
            itemIcon.setImageResource(item.icon)
            itemView.setOnClickListener {
                callback(item.id)
            }
        }
    }
}