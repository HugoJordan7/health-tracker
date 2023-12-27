package com.example.healthtracker
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    var listItems = mutableListOf<MainItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listItems.apply {
            add( MainItem(
                    id = 0,
                    text = R.string.imc,
                    icon = R.drawable.conditions))
            add( MainItem(
                    id = 1,
                    text = R.string.tmb,
                    icon = R.drawable.fire
            ))
            add( MainItem(
                    id = 2,
                    text = R.string.tgc,
                    icon = R.drawable.body_run))
            add( MainItem(
                id = 3,
                text = R.string.water,
                icon = R.drawable.water))
        }

        var mainAdapter = MainAdapter(listItems)
        var recyclerViewMain: RecyclerView = findViewById(R.id.main_rv)
        recyclerViewMain.adapter = mainAdapter
        recyclerViewMain.layoutManager = GridLayoutManager(this,2)
    }

    fun onClick(id: Int){
        when(id){
            0 -> startActivity(Intent(this,ImcActivity::class.java))
            1 -> startActivity(Intent(this,TmbActivity::class.java))
            2 -> startActivity(Intent(this,TgcActivity::class.java))
            3 -> startActivity(Intent(this,WaterActivity::class.java))
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

                itemText.setText(item.text)
                itemIcon.setImageResource(item.icon)
                itemView.setOnClickListener {
                    onClick(item.id)
                }
            }
        }
    }
}