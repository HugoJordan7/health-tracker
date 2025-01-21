package com.example.healthtracker.feature.main.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtracker.R;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<MainItem> items;
    private final ItemClickListener callback;

    public interface ItemClickListener {
        void onItemClick(int id);
    }

    public MainAdapter(List<MainItem> items, ItemClickListener callback) {
        this.items = items;
        this.callback = callback;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        public MainViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(MainItem item) {
            TextView itemText = itemView.findViewById(R.id.item_text);
            ImageView itemIcon = itemView.findViewById(R.id.item_icon);

            itemText.setText(item.getText());
            itemIcon.setImageResource(item.getIcon());

            itemView.setOnClickListener(v -> {
                callback.onItemClick(item.getId());
            });
        }
    }
}
