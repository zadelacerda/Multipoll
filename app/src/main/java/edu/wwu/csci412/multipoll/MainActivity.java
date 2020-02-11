package edu.wwu.csci412.multipoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import Data.Controller;

public class MainActivity extends AppCompatActivity {
    public static Controller controller;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        controller = new Controller();

        Button createPoll = this.findViewById(R.id.createPollMain);
        Button Groups = this.findViewById(R.id.Groups);

        createPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, Signin.class);
                startActivity(intent);
            }
        });

        Groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, Groups.class);
                startActivity(intent);
            }
        });
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateView(currentUser);
    }

    public static Controller getController() {
        return controller;
    }

}
