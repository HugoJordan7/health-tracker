package com.example.healthtracker
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    var listItems = mutableListOf<MainItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listItems.add(
            MainItem(
                id = 0,
                text = R.string.imc,
                color = Color.YELLOW,
                icon = R.drawable.ic_baseline_accessibility_new_24
            )
        )
        listItems.add(
            MainItem(
                id = 1,
                text = R.string.tmb,
                color = Color.CYAN,
                icon = R.drawable.ic_outline_directions_run_24
            )
        )
        listItems.add(
            MainItem(
                id = 2,
                text = R.string.tgc,
                color = Color.GREEN,
                icon = R.drawable.ic_baseline_fitness_center_24
            )
        )

        var mainAdapter = MainAdapter(listItems)
        var recyclerViewMain: RecyclerView = findViewById(R.id.main_rv)
        recyclerViewMain.adapter = mainAdapter
        recyclerViewMain.layoutManager = GridLayoutManager(this,2)

    }

    fun onClick(id: Int){
        when(id){
            0 -> startActivity(Intent(this,ImcActivity::class.java))
            //1 -> Log.i("teste","abrindo tmbbbbb")
            2 -> startActivity(Intent(this,ImcActivity::class.java))
        }
    }

    inner class MainAdapter(private var items: List<MainItem>): RecyclerView.Adapter<MainAdapter.MainViewHolder>(){
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item,parent,false)
            return MainViewHolder(view)
        }

        inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem){
                var itemText: TextView = itemView.findViewById(R.id.item_text)
                var itemIcon: ImageView = itemView.findViewById(R.id.item_icon)
                var itemBackground: LinearLayout = itemView.findViewById(R.id.item_layout)
                itemText.setText(item.text)
                itemIcon.setImageResource(item.icon)
                itemBackground.setBackgroundColor(item.color)
                itemView.setOnClickListener {
                    onClick(item.id)
                }
            }
        }
    }
}