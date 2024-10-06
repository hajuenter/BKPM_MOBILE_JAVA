package com.example.BKPM_E41231300;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Acara15_16 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterOnePiece adapter;
    private List<String> characterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acara1516);

        recyclerView = findViewById(R.id.recyclerView);

        // Mengisi daftar karakter One Piece
        characterList = new ArrayList<>();
        characterList.add("Monkey D. Luffy");
        characterList.add("Roronoa Zoro");
        characterList.add("Nami");
        characterList.add("Usopp");
        characterList.add("Sanji");
        characterList.add("Tony Tony Chopper");
        characterList.add("Nico Robin");
        characterList.add("Franky");
        characterList.add("Brook");
        characterList.add("Jinbei");

        // Setting adapter dan layout manager
        adapter = new AdapterOnePiece(characterList, new AdapterOnePiece.OnItemClickListener() {
            @Override
            public void onItemClick(String name) {
                // Tampilkan detail karakter saat diklik
                Intent intent = new Intent(Acara15_16.this, DetailOnePiece.class);
                intent.putExtra("CHARACTER_NAME", name);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
