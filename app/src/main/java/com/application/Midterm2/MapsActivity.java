package com.application.Midterm2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap gMap;
    private String venID;
    private String venName;
    private double venLatitude;
    private double venLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle venue = getIntent().getExtras();
        venID = venue.getString("ID");
        venName = venue.getString("name");
        venLatitude = venue.getDouble("latitude");
        venLongitude = venue.getDouble("longitude");
        setTitle(venName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng venue = new LatLng(venLatitude, venLongitude);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(venue, 16));

        Marker marker = gMap.addMarker(new MarkerOptions()
                .position(venue)
                .title(venName)
                .snippet("View on Foursquare"));
        marker.showInfoWindow();
        gMap.setOnInfoWindowClickListener(this);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            gMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://foursquare.com/v/" + venID));
        startActivity(browserIntent);
    }
}