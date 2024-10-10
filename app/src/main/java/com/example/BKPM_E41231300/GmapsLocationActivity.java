package com.example.BKPM_E41231300;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GmapsLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap gMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Marker userLocationMarker;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmaps_location);

        // Inisialisasi MapView
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        MapsInitializer.initialize(this);

        // Inisialisasi FusedLocationProviderClient untuk update lokasi real-time
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Membuat permintaan lokasi untuk update secara real-time
        locationRequest = new LocationRequest.Builder(10000) // Update setiap 10 detik
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMinUpdateIntervalMillis(5000) // Update minimal setiap 5 detik
                .build();

        // Callback untuk menerima update lokasi
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    updateUserLocation(location);
                }
            }
        };

        // Cek izin lokasi
        checkLocationPermission();
    }

    // Meminta izin lokasi jika belum diberikan
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            startLocationUpdates();
        }
    }

    // Callback ketika peta sudah siap
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        gMap.getUiSettings().setZoomControlsEnabled(true);

        // Cek lagi izin setelah peta siap
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            gMap.setMyLocationEnabled(true);
        }
    }

    // Mengupdate posisi user pada peta
    private void updateUserLocation(Location location) {
        if (gMap != null) {
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

            // Jika marker belum ada, tambahkan marker baru
            if (userLocationMarker == null) {
                userLocationMarker = gMap.addMarker(new MarkerOptions()
                        .position(userLocation)
                        .title("Lokasi Saya"));
            } else {
                // Pindahkan marker ke lokasi terbaru
                userLocationMarker.setPosition(userLocation);
            }

            // Pindahkan kamera ke lokasi pengguna
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
        }
    }

    // Memulai update lokasi
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    // Hentikan update lokasi ketika tidak diperlukan (misalnya ketika aplikasi di-pause)
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    // Menangani hasil permintaan izin lokasi
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
                if (gMap != null) {
                    gMap.setMyLocationEnabled(true);
                }
            } else {
                // Tampilkan pesan jika izin tidak diberikan
                // Anda bisa menambahkan logika untuk memberi tahu pengguna
            }
        }
    }
}
