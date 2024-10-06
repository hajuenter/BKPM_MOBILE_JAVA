package com.example.BKPM_E41231300;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterRyc extends RecyclerView.Adapter<AdapterRyc.ViewHolder> {

    private List<String> itemList; // Daftar item yang akan ditampilkan

    // Konstruktor untuk menerima data
    public AdapterRyc(List<String> itemList) {
        this.itemList = itemList;
    }

    // Membuat tampilan setiap item
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Menghubungkan tampilan dengan layout bawaan (TextView)
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    // Menghubungkan data ke tampilan
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Mengambil data dari itemList berdasarkan posisi
        String item = itemList.get(position);
        holder.textView.setText(item); // Menampilkan data di TextView
    }

    // Mengembalikan jumlah item
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder untuk menyimpan tampilan dari setiap item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1); // Menghubungkan TextView
        }
    }
}
