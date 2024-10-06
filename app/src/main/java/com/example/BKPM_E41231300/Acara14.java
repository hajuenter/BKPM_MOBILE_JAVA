package com.example.BKPM_E41231300;

import android.os.Bundle;
import android.view.View;  // Import View untuk menghindari error
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Acara14 extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listview;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acara14);

        listview = findViewById(R.id.listViewww); // Pastikan ID ini sesuai dengan ListView di layout
        adapter = ArrayAdapter.createFromResource(this, R.array.negara_array, android.R.layout.simple_list_item_1);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Tindakan yang dilakukan saat item diklik
        Toast.makeText(this, adapter.getItem(position), Toast.LENGTH_SHORT).show();
    }
}