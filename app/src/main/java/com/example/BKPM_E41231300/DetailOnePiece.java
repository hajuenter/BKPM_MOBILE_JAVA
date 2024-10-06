package com.example.BKPM_E41231300;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailOnePiece extends AppCompatActivity {

    private TextView tvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_one_piece);

        tvDetail = findViewById(R.id.tvDetail);

        // Mendapatkan nama karakter yang dikirim dari Acara 1516
        String characterName = getIntent().getStringExtra("CHARACTER_NAME");

        // Menampilkan detail karakter
        tvDetail.setText("Detail tentang " + characterName);
    }
}
