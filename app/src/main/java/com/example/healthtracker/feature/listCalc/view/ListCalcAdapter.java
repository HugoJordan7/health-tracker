package com.example.healthtracker.feature.listCalc.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtracker.R;
import com.example.healthtracker.model.Calc;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ListCalcAdapter extends RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder> {

    private final Context context;
    List<Calc> list;

    public ListCalcAdapter(Context context) {
        this.context = context;
        this.list = new LinkedList<>();
    }

    @NonNull
    @Override
    public ListCalcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ListCalcViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCalcViewHolder viewHolder, int i) {
        viewHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListCalcViewHolder extends RecyclerView.ViewHolder {

        public ListCalcViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Calc item) {
            TextView textView = (TextView) itemView;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    new Locale("pt", "BR")
            );
            String date = simpleDateFormat.format(item.getCreatedDate());

            String text = "";
            switch (item.getType()) {
                case "imc":
                    text = context.getString(R.string.list_calc_register_imc, date, item.getRes());
                    break;
                case "tmb":
                    text = context.getString(R.string.list_calc_register_tmb, date, item.getRes());
                    break;
                case "bpm":
                    text = context.getString(R.string.list_calc_register_bpm, item.getSituation(), date, item.getRes());
                    break;
            }
            textView.setText(text);
        }

    }
}
