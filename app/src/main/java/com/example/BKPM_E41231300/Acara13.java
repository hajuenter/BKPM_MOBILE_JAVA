package com.example.BKPM_E41231300;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Acara13 extends AppCompatActivity {

    private RecyclerView recyclerView; // Deklarasi untuk RecyclerView
    private Spinner spinner; // Deklarasi untuk Spinner
    private AutoCompleteTextView autoCompleteTextView; // Deklarasi untuk AutoCompleteTextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acara13); // Menghubungkan ke layout XML

        // Inisialisasi komponen dari XML
        recyclerView = findViewById(R.id.recyclerView);
        spinner = findViewById(R.id.spinner);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        // Memanggil fungsi untuk mengatur RecyclerView, Spinner, dan AutoCompleteTextView
        setupRecyclerView();
        setupSpinner();
        setupAutoCompleteTextView();
    }

    // Fungsi untuk mengatur RecyclerView (daftar item yang bisa di-scroll)
    private void setupRecyclerView() {
        // Membuat daftar item sederhana
        List<String> itemList = new ArrayList<>();
        itemList.add("Item 1");
        itemList.add("Item 2");
        itemList.add("Item 3");
        itemList.add("Item 1");
        itemList.add("Item 2");
        itemList.add("Item 3");
        itemList.add("Item 1");
        itemList.add("Item 2");
        itemList.add("Item 3");
        itemList.add("Item 1");
        itemList.add("Item 2");
        itemList.add("Item 3");
        itemList.add("Item 1");
        itemList.add("Item 2");
        itemList.add("Item 3");
        itemList.add("Item 1");
        itemList.add("Item 2");
        itemList.add("Item 3");
        itemList.add("Item 1");
        itemList.add("Item 2");
        itemList.add("Item 3");

        // Mengatur tampilan vertikal untuk RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Mengatur adapter untuk menghubungkan data ke RecyclerView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_2, itemList);
        recyclerView.setAdapter(new AdapterRyc(itemList));
    }

    // Fungsi untuk mengatur Spinner (dropdown pilihan)
    private void setupSpinner() {
        // Daftar pilihan untuk Spinner
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("Pilihan 1");
        spinnerItems.add("Pilihan 2");
        spinnerItems.add("Pilihan 3");

        // Mengatur adapter untuk Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    // Fungsi untuk mengatur AutoCompleteTextView (input dengan saran)
    private void setupAutoCompleteTextView() {
        // Daftar saran untuk AutoCompleteTextView
        List<String> autoCompleteItems = new ArrayList<>();
        autoCompleteItems.add("Saran 1");
        autoCompleteItems.add("Saran 2");
        autoCompleteItems.add("Saran 3");

        // Mengatur adapter untuk AutoCompleteTextView
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteItems);
        autoCompleteTextView.setAdapter(autoCompleteAdapter);
        autoCompleteTextView.setThreshold(1); // Saran muncul setelah 1 karakter diketik
    }
}
