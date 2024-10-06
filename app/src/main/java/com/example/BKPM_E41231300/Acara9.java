package com.example.BKPM_E41231300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Acara9 extends AppCompatActivity {

    private Button btnrelative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_acara9);

        btnrelative = findViewById(R.id.btnrelative);

        // Set OnClickListener untuk tombol
        btnrelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat Intent untuk berpindah ke aktivitas Relative
                Intent nnn = new Intent(Acara9.this, Relative.class);
                // Memulai aktivitas Relative
                startActivity(nnn);
            }
        });
    }
}
