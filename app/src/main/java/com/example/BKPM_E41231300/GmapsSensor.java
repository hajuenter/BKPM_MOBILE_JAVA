package com.example.BKPM_E41231300;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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

public class GmapsSensor extends AppCompatActivity implements OnMapReadyCallback, SensorEventListener {

    private MapView mapView;
    private GoogleMap gMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Marker userLocationMarker;
    private TextView sensorDataTextView;

    private SensorManager sensorManager;
    private MediaPlayer mediaPlayer;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmaps_sensor);

        // Inisialisasi MapView
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        MapsInitializer.initialize(this);

        // Inisialisasi sensor
        sensorDataTextView = findViewById(R.id.sensor_data);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Inisialisasi FusedLocationProviderClient untuk update lokasi real-time
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Membuat permintaan lokasi untuk update secara real-time
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000); // Update setiap 10 detik
        locationRequest.setFastestInterval(5000); // Update minimal setiap 5 detik
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

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

        // Inisialisasi media player untuk suara
        mediaPlayer = MediaPlayer.create(this, R.raw.lagu);
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            startLocationUpdates();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        gMap.getUiSettings().setZoomControlsEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                gMap.setMyLocationEnabled(true);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUserLocation(Location location) {
        if (gMap != null) {
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

            if (userLocationMarker == null) {
                userLocationMarker = gMap.addMarker(new MarkerOptions()
                        .position(userLocation)
                        .title("Lokasi Saya"));
            } else {
                userLocationMarker.setPosition(userLocation);
            }

            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
        }
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        String sensorData = "";

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                sensorData = String.format("Accelerometer: X=%.2f, Y=%.2f, Z=%.2f", values[0], values[1], values[2]);
                break;
            case Sensor.TYPE_GYROSCOPE:
                sensorData = String.format("Gyroscope: X=%.2f, Y=%.2f, Z=%.2f", values[0], values[1], values[2]);
                break;
            case Sensor.TYPE_LIGHT:
                sensorData = String.format("Light: %.2f", values[0]);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                sensorData = String.format("Magnetic Field: X=%.2f, Y=%.2f, Z=%.2f", values[0], values[1], values[2]);
                break;
            case Sensor.TYPE_PROXIMITY:
                sensorData = String.format("Proximity: %.2f", values[0]);
                break;
        }

        // Tampilkan data sensor di UI
        sensorDataTextView.setText(sensorData);

        // Mainkan suara ketika terjadi perubahan pada sensor
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Tidak digunakan untuk sekarang
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        startLocationUpdates();

        // Daftarkan listener sensor
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (light != null) {
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (proximity != null) {
            sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
        // Hentikan sensor ketika aplikasi dijeda
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        fusedLocationClient.removeLocationUpdates(locationCallback);
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Lepaskan media player ketika tidak digunakan lagi
            mediaPlayer = null;
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
                if (gMap != null) {
                    try {
                        gMap.setMyLocationEnabled(true);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(this, "Izin lokasi diperlukan untuk menampilkan peta.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
