package com.example.BKPM_E41231300;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GeminiActivity extends AppCompatActivity {

    // Deklarasi variabel untuk komponen tampilan
    private TextView tvhasil; // TextView untuk menampilkan hasil dari API
    private EditText etPerintah; // EditText untuk input perintah oleh pengguna
    private Button btnCari, btnClear; // Button untuk memulai pencarian
    private ScrollView scrollView; // ScrollView untuk memungkinkan hasil di-scroll

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Mengaktifkan tampilan layar penuh tanpa inset
        setContentView(R.layout.activity_gemini); // Menghubungkan layout XML dengan Activity

        // Menghubungkan komponen tampilan dengan variabel yang telah dideklarasikan
        tvhasil = findViewById(R.id.tvhasil);
        etPerintah = findViewById(R.id.et_perintah);
        btnCari = findViewById(R.id.btn_cari);
        scrollView = findViewById(R.id.scroll_view_hasil);
        btnClear = findViewById(R.id.btn_clear);

        // Mengatur aksi ketika tombol "Cari" diklik oleh pengguna
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = etPerintah.getText().toString().trim();

                // Validasi untuk memastikan input tidak kosong
                if (userInput.isEmpty()) {
                    etPerintah.setError("Masukkan perintah terlebih dahulu");
                    etPerintah.requestFocus();
                } else {
                    // Menampilkan pesan loading dan menghapus hasil lama
                    tvhasil.setText("Memproses...");
                    // Jika input valid, panggil API di background thread
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> ApiGeminiCall(userInput));
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvhasil.setText("");
            }
        });
    }

    // Metode untuk memanggil API Gemini dengan input dari EditText
    public void ApiGeminiCall(String userInput) {
        // Inisialisasi model generatif dari Google dengan nama model dan API key
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", "AIzaSyCiDshvhf7vBFD5kwBEgbGuA1qPOjP8vnQ");
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        // Membuat konten yang akan dikirim ke API menggunakan input dari pengguna
        Content content = new Content.Builder()
                .addText(userInput) // Menambahkan teks yang diambil dari EditText ke dalam permintaan API
                .build();

        // Mengirim permintaan ke model generatif dan mendapatkan hasilnya sebagai `ListenableFuture`
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        // Menambahkan callback untuk menangani hasil dari permintaan API
        Futures.addCallback(
                response,
                new FutureCallback<GenerateContentResponse>() {
                    // Jika permintaan sukses dan hasil diterima
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        String resultText = result.getText();
                        // Set teks tersebut ke dalam TextView untuk ditampilkan kepada pengguna di main thread
                        runOnUiThread(() -> {
                            tvhasil.setText(resultText); // Menampilkan hasil baru
                            scrollView.scrollTo(0, 0); // Mengembalikan posisi scroll ke atas
                        });
                    }

                    // Jika permintaan gagal (misalnya karena kesalahan jaringan)
                    @Override
                    public void onFailure(Throwable t) {
                        // Cetak error untuk keperluan debugging
                        t.printStackTrace();
                        // Tampilkan pesan error ke pengguna
                        runOnUiThread(() -> {
                            tvhasil.setText("Terjadi kesalahan saat memproses permintaan.");
                            Toast.makeText(GeminiActivity.this, "Kesalahan: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        });
                    }
                },
                Executors.newSingleThreadExecutor()); // Eksekusi callback menggunakan `executor` agar tetap asinkron
    }
}
