package com.example.myrouting;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import static com.example.myrouting.AddNewRouteActivity.addressPos;
import static com.example.myrouting.AddNewRouteActivity.finalAddressPos;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    Button makeRoute;
    public static GoogleMap mMap;
    LatLng source = addressPos;
    LatLng dest = finalAddressPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeRoute = (Button) findViewById(R.id.MakeRoute);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapMain);
        mapFragment.getMapAsync(this);

        MakeRoute route = new MakeRoute();
        if(source != null && dest != null)
            route.drawRoute(mMap, getApplicationContext(), source, dest, "en");
        final Intent makeRouteIntent = new Intent(this, AddNewRouteActivity.class);

        View.OnClickListener makeButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(makeRouteIntent);
            }
        };
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
            mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        makeRoute.setOnClickListener(makeButton);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

}
