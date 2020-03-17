package edu.wwu.csci412.multipoll.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (locationPermissionGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            init();
        }
    }

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    // Widgets
    private EditText searchText;

    //vars
    private Boolean locationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        searchText = findViewById(R.id.inputSearch);
        getLocationPermission();
    }

    private void init() {
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                    geoLocate();
                }
                return false;
            }
        });
        hideSoftKeyboard();
    }

    private void geoLocate() {
        String searchFor = searchText.getText().toString();
        Geocoder gc = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = gc.getFromLocationName(searchFor, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }

    private void getDeviceLocation(){
        // Get device location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(locationPermissionGranted){

                final Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        // Found location
                        if (task.isSuccessful()){
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "My Location");
                        }
                        else {
                            Toast.makeText(MapsActivity.this, "Cannot get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e){
            Log.e(TAG, "SecurityException @ getDeviceLocation: " + e.getMessage() );
        }
    }

    // Adjust camera
    private void moveCamera(LatLng latLng, float zoom, String title){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    // Initialize the map
    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    // Get location permissions
    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationPermissionGranted = true;
                initMap();
            }
            else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    // Permission results after request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            // Permission failed
                            locationPermissionGranted = false;
                            return;
                        }
                    }

                    // Permission Granted
                    locationPermissionGranted = true;
                    //initialize the map
                    initMap();
                }
        }
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
