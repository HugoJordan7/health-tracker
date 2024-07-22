package com.example.healthtracker.feature.listCalc.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthtracker.R
import com.example.healthtracker.model.Calc
import java.text.SimpleDateFormat
import java.util.Locale

class ListCalcAdapter(
    private val context: Context
): RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder>() {

    var list: List<Calc> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCalcViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ListCalcViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ListCalcViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListCalcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemCalc: Calc) {
            val textView: TextView = itemView as TextView
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
            val date = sdf.format(itemCalc.createdDate)
            val stringId: Int = when (itemCalc.type) {
                "imc" -> R.string.list_calc_register_imc
                "tmb" -> R.string.list_calc_register_tmb
                "bpm" -> R.string.list_calc_register_bpm
                else -> 0
            }

            if (itemCalc.type == "imc" || itemCalc.type == "tmb") {
                textView.text = context.getString(stringId, date, itemCalc.res)
            } else {
                textView.text = context.getString(stringId, itemCalc.situation, date, itemCalc.res)
            }

        }
    }

}