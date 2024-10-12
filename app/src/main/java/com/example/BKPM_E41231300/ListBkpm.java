package com.example.BKPM_E41231300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListBkpm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_bkpm);

        ListView listBkpmMobile = findViewById(R.id.list_bkpm);

        List<String> list = new ArrayList<>();
        list.add("Acara 9 Linier dan Relative"); //0
        list.add("Acara 10 Constrain dan Frame");
        list.add("Acara 11 Table Layout");
        list.add("Acara 12 Scroll View");
        list.add("Acara 13 RecyclerView, Spinner, dan AutoComplete");
        list.add("Acara 14 List View");
        list.add("Acara 15-16 Card View");
        list.add("Acara 17-20 Fragment");
        list.add("Acara 33 Google Maps");
        list.add("Acara Google Maps Search dan Control");
        list.add("Acara Google Maps Real Time");
        list.add("Acara Google Maps Sensor");

        // Menggunakan this untuk mendapatkan context
        ArrayAdapter<String> adapterBkpm = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listBkpmMobile.setAdapter(adapterBkpm);

        listBkpmMobile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;

                if (position == 0) {
                    intent = new Intent(ListBkpm.this, Acara9.class);
                    startActivity(intent);
                } else if (position == 1) {
                    intent = new Intent(ListBkpm.this, Acara10.class);
                    startActivity(intent);
                } else if (position == 2) {
                    intent = new Intent(ListBkpm.this, Acara11.class);
                    startActivity(intent);
                } else if (position == 3) {
                    intent = new Intent(ListBkpm.this, Acara12.class);
                    startActivity(intent);
                } else if (position == 4) {
                    intent = new Intent(ListBkpm.this, Acara13.class);
                    startActivity(intent);
                } else if (position == 5) {
                        intent = new Intent(ListBkpm.this, Acara14.class);
                        startActivity(intent);
                } else if (position == 6) {
                    intent = new Intent(ListBkpm.this, Acara15_16.class);
                    startActivity(intent);
                } else if (position == 7) {
                    intent = new Intent(ListBkpm.this, Acara17_20.class);
                    startActivity(intent);
                } else if (position == 8) {
                    intent = new Intent(ListBkpm.this, GmapsActivity.class);
                    startActivity(intent);
                } else if (position == 9) {
                    intent = new Intent(ListBkpm.this, GmapsWithSearchActivity.class);
                    startActivity(intent);
                } else if (position == 10) {
                    intent = new Intent(ListBkpm.this, GmapsLocationActivity.class);
                    startActivity(intent);
                } else if (position == 11) {
                    intent = new Intent(ListBkpm.this, AcaraSensor.class);
                    startActivity(intent);
                } else {
                    // Default intent jika item tidak cocok dengan yang di atas
                    intent = new Intent(ListBkpm.this, ListBkpm.class);
                    startActivity(intent);
                }
            }
        });
    }
}
