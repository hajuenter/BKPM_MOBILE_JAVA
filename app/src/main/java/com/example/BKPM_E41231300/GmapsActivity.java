package com.example.BKPM_E41231300;

import android.os.Bundle; // Untuk mengelola data dalam bundle
import androidx.annotation.NonNull; // Untuk menghindari NullPointerException
import androidx.appcompat.app.AppCompatActivity; // Untuk membuat activity menggunakan AppCompat
import com.google.android.gms.maps.CameraUpdateFactory; // Untuk memindahkan kamera peta
import com.google.android.gms.maps.GoogleMap; // Untuk mengakses peta
import com.google.android.gms.maps.OnMapReadyCallback; // Callback ketika peta siap
import com.google.android.gms.maps.MapView; // Untuk menggunakan MapView
import com.google.android.gms.maps.MapsInitializer; // Untuk inisialisasi peta
import com.google.android.gms.maps.model.LatLng; // Untuk menyimpan koordinat (latitude, longitude)
import com.google.android.gms.maps.model.MarkerOptions; // Untuk menambahkan marker pada peta

// Main activity yang mengextends AppCompatActivity dan mengimplementasikan OnMapReadyCallback
public class GmapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    // Deklarasi MapView dan GoogleMap
    private MapView mapView; // MapView untuk menampilkan peta
    private GoogleMap gMap; // Objek GoogleMap untuk mengelola peta

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmaps);

        // Menghubungkan MapView dari layout ke variabel mapView
        mapView = findViewById(R.id.map_view);

        // Memanggil onCreate pada MapView dengan savedInstanceState
        mapView.onCreate(savedInstanceState);

        // Memanggil peta secara asinkron dan mengatur callback ke 'this'
        mapView.getMapAsync(this);

        // Inisialisasi peta
        MapsInitializer.initialize(this);
    }

    // Callback ketika peta sudah siap digunakan
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap; // Menyimpan objek GoogleMap ke variabel gMap


        LatLng nganjukBro = new LatLng(-7.60578393618668, 111.88443387701996);

        // Menambahkan marker pada peta di lokasi Nganjuk
        googleMap.addMarker(new MarkerOptions().position(nganjukBro).title("Musholla Guri Omah"));

        // Memindahkan kamera ke lokasi dengan zoom level 12
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nganjukBro, 12));
    }
//
//    // Method yang dipanggil saat activity resume
//    @Override
//    protected void onResume() {
//        super.onResume(); // Memanggil method superclass
//        mapView.onResume(); // Melanjutkan lifecycle MapView
//    }
//
//    // Method yang dipanggil saat activity pause
//    @Override
//    protected void onPause() {
//        super.onPause(); // Memanggil method superclass
//        mapView.onPause(); // Menghentikan MapView
//    }
//
//    // Method yang dipanggil saat activity dihancurkan
//    @Override
//    protected void onDestroy() {
//        super.onDestroy(); // Memanggil method superclass
//        mapView.onDestroy(); // Menghancurkan MapView
//    }
//
//    // Method yang dipanggil saat sistem memerlukan memori lebih
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory(); // Memanggil method superclass
//        mapView.onLowMemory(); // Menangani memori rendah pada MapView
//    }
//
//    // Method untuk menyimpan state instance saat activity disimpan
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState); // Memanggil method superclass
//        mapView.onSaveInstanceState(outState); // Menyimpan state MapView
//    }
}
