package com.gabriel.taxifee;

import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.services.android.navigation.ui.v5.location.LocationEngineConductorListener;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationEngineConductorListener,
        PermissionsListener, MapboxMap.OnMapClickListener {

    private MapView mapView;
    private MapboxMap map;
    private Button calculate;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originLocation;
    private Point originPosition, destinationPosition;
    private Marker destinationMarker;
    private NavigationMapRoute navigationMapRoute;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString())
    }
}