package com.example.healthtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthtracker.model.Calc
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ListCalcActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)
        val type = intent?.extras?.getString("type") ?: throw Exception("The type is not specified")

        val arrowBackButton: ImageButton = findViewById(R.id.arrow_refs_history)
        arrowBackButton.setOnClickListener {
            finish()
        }

        val clearHistoryButton: ImageButton = findViewById(R.id.delete_history_button)
        clearHistoryButton.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle(R.string.dialog_title_delete_history)
                setPositiveButton(R.string.yes){_,_->
                    Thread{
                        val app = application as App
                        val dao = app.db.calcDao()
                        if(dao.getRegisterByType(type).isEmpty()){
                            runOnUiThread{
                                Toast.makeText(this@ListCalcActivity,R.string.toast_delete_history_error,Toast.LENGTH_LONG).show()
                            }
                            return@Thread
                        }
                        dao.deleteAllByType(type)
                        runOnUiThread{
                            Toast.makeText(this@ListCalcActivity,R.string.toast_delete_history,Toast.LENGTH_LONG).show()
                            finish()
                            startActivity(
                                Intent(this@ListCalcActivity,ListCalcActivity::class.java).putExtra("type",type)
                            )
                        }
                    }.start()
                }
                setNegativeButton(R.string.back){_,_->}
                create()
                show()
            }
        }

        lateinit var imcList: List<Calc>
        Thread{
            val app = application as App
            val dao = app.db.calcDao()
            imcList = dao.getRegisterByType(type)
            runOnUiThread{
                var adapter = ListCalcAdapter(imcList)
                var rvListCalc: RecyclerView = findViewById(R.id.rv_list_calc)
                rvListCalc.adapter = adapter
                rvListCalc.layoutManager = LinearLayoutManager(this)
            }
        }.start()
    }

    inner class ListCalcAdapter(var list: List<Calc>): RecyclerView.Adapter<ListCalcViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCalcViewHolder {
            val layout = layoutInflater.inflate(android.R.layout.simple_list_item_1,parent,false)
            return ListCalcViewHolder(layout)
        }
        override fun onBindViewHolder(holder: ListCalcViewHolder, position: Int) {
            holder.bind(list[position])
        }
        override fun getItemCount(): Int {
            return list.size
        }
    }

    inner class ListCalcViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(itemCalc: Calc){
            val textView: TextView = itemView as TextView
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt","BR"))
            sdf.timeZone = TimeZone.getTimeZone("UTC-3")
            val date = sdf.format(itemCalc.createdDate)
            val stringId: Int = when(itemCalc.type){
                "imc" -> R.string.list_calc_register_imc
                "tmb" -> R.string.list_calc_register_tmb
                "tgc"-> R.string.list_calc_register_tgc
                else -> 0
            }
            textView.text = getString(stringId, date, itemCalc.res)
        }
    }
}