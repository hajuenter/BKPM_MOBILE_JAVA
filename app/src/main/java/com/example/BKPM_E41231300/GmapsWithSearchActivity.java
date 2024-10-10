package com.example.BKPM_E41231300;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.widget.ZoomControls;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GmapsWithSearchActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap gMap;
    private SearchView searchView;
    private ZoomControls zoomControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmaps_with_search);

        // Inisialisasi MapView
        mapView = findViewById(R.id.map_view);
        searchView = findViewById(R.id.search_view);
        zoomControls = findViewById(R.id.zoom_controls);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        MapsInitializer.initialize(this);

        // Setup pencarian lokasi
        setupSearchFunctionality();

        // Setup kontrol zoom
        setupZoomControls();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        // Lokasi awal (misal: Nganjuk)
        LatLng defaultLocation = new LatLng(-7.60578393618668, 111.88443387701996);
        gMap.addMarker(new MarkerOptions().position(defaultLocation).title("Musholla Guri Omah"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12));
    }

    private void setupSearchFunctionality() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(GmapsWithSearchActivity.this, Locale.getDefault());

                    try {
                        // Geocoding untuk mendapatkan koordinat dari nama lokasi
                        addressList = geocoder.getFromLocationName(location, 1);

                        if (addressList.size() > 0) {
                            Address address = addressList.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                            // Menambahkan marker di lokasi yang dicari
                            gMap.addMarker(new MarkerOptions().position(latLng).title(location));
                            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                        } else {
                            Toast.makeText(getApplicationContext(), "Lokasi tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setupZoomControls() {
        zoomControls.setOnZoomInClickListener(v -> {
            gMap.animateCamera(CameraUpdateFactory.zoomIn());
        });

        zoomControls.setOnZoomOutClickListener(v -> {
            gMap.animateCamera(CameraUpdateFactory.zoomOut());
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
