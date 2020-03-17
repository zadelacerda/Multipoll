package edu.wwu.csci412.multipoll.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;

//import static edu.wwu.csci412.multipoll.Controller.MapsActivity.mMap;

public class MainActivity extends AppCompatActivity {
    public static Controller controller;
    public static DatabaseController dbcontroller;
    private static User user;

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    public static Boolean mLocationPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            if (mMap != null) {
//                mMap.setMyLocationEnabled(true);
//                mLocationPermissionGranted = true;
//            }
//        } else {
//            // Permission to access the location is missing. Show rationale and request permission
//            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
//                    Manifest.permission.ACCESS_FINE_LOCATION, true);
//        }

        controller = new Controller();
        dbcontroller = new DatabaseController();
        user = controller.getUser();

        Button maps = findViewById(R.id.mapBtn);
        maps.setText("MAPS");
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        // Toolbar setup
        getSupportActionBar().setTitle("");

        Button createPoll = this.findViewById(R.id.createPoll);
        Button Groups = this.findViewById(R.id.Groups);

        createPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, ChooseGroup.class);
                startActivity(intent);
            }
        });

        Groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int noGroup = 1;
                if (!user.getGroups().isEmpty()) {
                    noGroup = 0;
                }
                Intent intent;
                switch (noGroup) {
                    case 0:
                        intent = new Intent(MainActivity.this, Groups.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, NewGroup.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    public static Controller getController() {
        return controller;
    }

}
