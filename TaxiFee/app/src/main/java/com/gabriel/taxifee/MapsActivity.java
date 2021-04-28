package com.gabriel.taxifee;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity {

    // Initialize variables
    private View decorView;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private Button calculate_distance;
    private Location location;
    FusedLocationProviderClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getFullScreen();

        // Assign variables
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        // Initialize fused location
        client = LocationServices.getFusedLocationProviderClient(this);

        // Check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // When permission granted
            // Call method
            getCurrentLocation();
        } else {
            // When permission denied
            // Request permission
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
        }

    }



    public void getCurrentLocation() {
        // Initialize task location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(location -> {
            // When success
            if (location != null) {
                mapFragment.getMapAsync(googleMap -> {
                    mMap = googleMap;

                    mMap.setMyLocationEnabled(true);

                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                    // Creating marker options
                    MarkerOptions options = new MarkerOptions().position(latLng);

                    // Changing marker
                    options.alpha(0);

                    // Zoom map
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));

                    mMap.addMarker(options);

                    // Adding new marker for user to select destination
                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            MarkerOptions newMarkerOption = new MarkerOptions();
                            // Setting position
                            newMarkerOption.position(latLng);
                            // Setting Latitude and Longitude on Marker
                            newMarkerOption.title(latLng.latitude + " : " + latLng.longitude);
                            // Clearing the previously Click position
                            mMap.clear();
                            // Zooming to marker
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            // Adding marker to map
                            mMap.addMarker(newMarkerOption);
                        }
                    });
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // When permission granted
                // Call method
                getCurrentLocation();
            }
        }
    }


    private void getFullScreen() {
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}