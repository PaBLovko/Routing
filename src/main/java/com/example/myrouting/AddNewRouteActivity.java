package com.example.myrouting;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class AddNewRouteActivity {

    private final MyFragmentActivity fragmentActivity = new MyFragmentActivity();
    private final MyOnMapReadyCallback onMapReadyCallback = new MyOnMapReadyCallback();
    Button build;
    EditText start, end;
    static public LatLng addressPos, finalAddressPos;

    public static GoogleMap mMap;


    public void getDirections(String start, String end) {

        new GetDirections().getAsyncTask().execute(start, end);
    }

    public FragmentActivity getFragmentActivity() {
        return fragmentActivity;
    }

    public OnMapReadyCallback getOnMapReadyCallback() {
        return onMapReadyCallback;
    }

    private class MyFragmentActivity extends FragmentActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_new_route);
            build = (Button) findViewById(R.id.Build);
            start = (EditText) findViewById(R.id.Whence);
            end = (EditText) findViewById(R.id.Where);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map));
            mapFragment.getMapAsync(onMapReadyCallback);

            View.OnClickListener makeButton = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!(start.getText().toString().equals("") || end.getText().toString().equals("")))
                    {
                        getDirections(start.getText().toString(), end.getText().toString());
                    }
                    LatLng source = addressPos;
                    LatLng dest = finalAddressPos;
                    MakeRoute route = new MakeRoute();
                    route.drawRoute(mMap, getApplicationContext(), source, dest, "en");
                }
            };

            build.setOnClickListener(makeButton);
        }
    }

    private class MyOnMapReadyCallback implements OnMapReadyCallback {
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
        }
    }
}
